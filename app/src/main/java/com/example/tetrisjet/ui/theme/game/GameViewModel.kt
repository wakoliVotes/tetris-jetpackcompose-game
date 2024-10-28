package com.example.tetrisjet.ui.theme.game

import android.content.Intent
import android.graphics.Path.Direction
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrisjet.ui.theme.game.GameStatus.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.consumeAsFlow

class GameViewModel : ViewModel() {
    val state = mutableStateOf(State.initial(12 to 24))
    private val intents = Channel<Intent>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            handleIntents()
        }
    }

    fun consume(intent: Intent) {
        intents.trySend(intent).isSuccess
    }

    private suspend fun handleIntents() {
        withContext(Dispatchers.Default) {
            intents.consumeAsFlow().collect { intent ->
                state.value = reduce(state.value, intent)
            }
        }
    }

    private fun reduce(state: State, intent: Intent): State = when (intent) {
        Intent.Tap -> if (state.gameStatus == GameOver) reduce(state, Intent.Restart) else reduce(
            state,
            Intent.Rotate
        )

        Intent.Restart -> State.initial(state.size)
        Intent.Pause -> state.copy(gameStatus = Pause)
        Intent.Resume -> state.copy(gameStatus = InProgress)
        is Intent.Swipe -> {
            val offset = intent.direction.toOffset()
            val newHero =
                if (intent.force && intent.direction == Direction.DOWN) state.projection else state.hero.move(
                    offset
                )
            val newProjection = newHero.createProjection(state.blocks)
            if (newHero.isValid(state.blocks)) state.copy(
                hero = newHero,
                projection = newProjection
            ) else state
        }

        Intent.Rotate -> {
            val newHero = state.hero.rotate().adjustOffset(state.size)
            val newProjection = newHero.createProjection(state.blocks)
            if (newHero.isValid(state.blocks)) state.copy(
                hero = newHero,
                projection = newProjection
            ) else state


        }

        Intent.GameTick -> {
            if (state.gameStatus == InProgress) {
                if (state.hero.move(0 to 1).isValid(state.blocks)) {
                    val newTick = state.tick + 1
                    reduce(state, Intent.Swipe(Direction.DOWN))
                        .copy(tick = newTick, velocity = newTick % 10L)
                } else {
                    val newHero = state.heroBag.first()
                    val (newBlocks, destroyedRows) = state.blocks.modifyBlocks(state.hero)
                    val newGameStatus =
                        if (newHero.isValid(state.blocks)) state.gameStatus else GameOver
                    state.copy(
                        blocks = newBlocks,
                        hero = newHero,
                        projection = newHero.createProjection(newBlocks),
                        heroBag = state.heroBag.minus(newHero)
                            .ifEmpty { TetrisGameBlock.generateBag(state.size) },
                        gameStatus = newGameStatus,
                        score = state.score + calculateScore(destroyedRows)
                    )
                }
            } else state
        }
    }

    data class State(
        val size: BoardSize,
        val hero: TetrisGameBlock,
        val projection: TetrisGameBlock,
        val heroBag: List<TetrisGameBlock>,
        val blocks: Board,
        val velocity: Long,
        var gameStatus: GameStatus,
        val tick: Int,
        val score: Int
    ) {
        companion object {
            fun initial(size: BoardSize): State {
                val heroBag = TetrisGameBlock.generateBag(size)
                val hero = heroBag.first()
                return State(
                    size = size,
                    hero = hero,
                    projection = hero.move(0 to size.second),
                    heroBag = heroBag.minus(hero),
                    blocks = (0 until size.second).map {
                        (0 until size.first).map { Color.Unspecified }
                    },
                    velocity = 1,
                    gameStatus = InProgress,
                    tick = 0,
                    score = 0
                )
            }
        }
    }

    sealed class Intent {
        data class Swipe(val direction: Direction, val force: Boolean = false) : Intent()
        data object Restart : Intent()
        data object Pause : Intent()
        data object Resume : Intent()
        data object Rotate : Intent()
        data object Tap : Intent()
        data object GameTick : Intent()
    }
}
typealias Board = List<List<Color>>
typealias BoardSize = Pair<Int, Int>