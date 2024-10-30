package com.example.tetrisjet.ui.theme.presentation

// TODO - Add dependencies - 28/10/2024
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.tetrisjet.ui.theme.TetrisjetTheme
import com.example.tetrisjet.ui.theme.game.GameViewModel
import kotlinx.coroutines.channels.ticker
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tetrisjet.ui.theme.game.TetrisGameBlock
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlin.math.min


@OptIn(ObsoleteCoroutinesApi::class)
@Composable
fun TetrisGame(){
    val viewModel = viewModel<GameViewModel>()
    val state by viewModel.state

    val lifecyleOwner = LocalLifecycleOwner.current

    val dragObserver = with(LocalDensity.current) {
        TetrisSwipeObserver(TouchSlop.toPx(), MinFlingVelocity.toPx()) {
            viewModel.consume(Intent.Swipe(it, true))
        }
    }
    val onTap: (Offset) -> Unit = { viewModel.consume(Intent.Tap)}
    val tickerChannel = remember { ticker(delayMillis = 300 / state.velocity) }

    val lifecleOwner = LifecycleOwner {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            viewModel.consume(Intent.Resume)
        }
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            viewModel.consume(Intent.Pause)
        }
    }
    lifecyleOwner.lifecycle.addObserver(observer)
    onDispose {
        lifecleOwner.lifecycle.removeObserver(observer)
    }
}
// TODO - Fix
LaunchedTask {
    for (event in tickerChannel) {
        viewModel.consumer(Intent.GameTick)
    }
}

// TODO - Add box, statistics, and gameover composable functions

@Composable
fun Statistic(state: GameViewModel.State, modifier: Modifier = Modifier) {
    Row (modifier.padding(all = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Score: ${state.score}")
        Text(text = "Next", modifier = Modifier.padding(start = 16.dp))
        NextHero(block = state.heroBad.first().rotate(), modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun GameOver(modifier: Modifier) {
    Text(
        text = "Game Over!",
        style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
        textAlign = TextAlign.Center,
        modifier = modifier.background(Color.Red.copy(alpha = 0.7F))
    )
}

@Composable
fun Board(state: GameViewModel.State, modifier: Modifier = Modifier) {
    Canvas(
        modifier
            .fillMaxSize()
            .drawBehind {
                drawBoarderBackground(state.size)
            }
    ) {
        val blockSize = min(
            size.height / state.size.second.toFloat(),
            size.width / state.size.first.toFloat()
        )
        drawBoard(state.blocks, blockSize)
        drawHero(state.hero, blockSize)
        drawProjection(state.projection, blockSize)
    }
}

@Composable
fun NextHero(block: TetrisGameBlock, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        drawHero(block.adjustOffset(4 to 1), 12.dp.toPx())
    }
}

// Preview the game - Test and correct
@Preview
@Composable
fun DefaultPreview(){
    TetrisjetTheme {
        TetrisGame()
    }
}
//TODO - Fix Game Errors - 29/10/2024