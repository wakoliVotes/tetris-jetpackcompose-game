package com.example.tetrisjet.ui.theme.game

import com.example.tetrisjet.ui.theme.game.Direction
import android.view.MotionEvent
import androidx.compose.ui.graphics.Color

enum class Direction {
    LEFT, RIGHT, DOWN;
}

fun calculateScore(linesDestroyed: Int) = when (linesDestroyed) {
    1 -> 100
    2 -> 300
    3 -> 700
    4 -> 1500
    else -> 0
}

// FIXME - Swipe direction, enum class - 30/10/2024 - NG

val shapeVariants = listOf(
    listOf(Pair(0, -1), Pair(0, 0), Pair(-1, 0), Pair(-1, 1)) to Color(0xFF3D76B5),
    listOf(Pair(0, -1), Pair(0, 0), Pair(1, 0), Pair(1, 1)) to Color(0xFFA369B8),
    listOf(Pair(0, -1), Pair(0, 0), Pair(0, 1), Pair(0, 2)) to Color(0xFFFF0128),
    listOf(Pair(0, 1), Pair(0, 0), Pair(0, -1), Pair(1, 0)) to Color(0xFF43D462),
    listOf(Pair(0, 0), Pair(-1, 0), Pair(0, -1), Pair(-1, -1)) to Color(0xFFFBCD05),
    listOf(Pair(-1, -1), Pair(0, -1), Pair(0, 0), Pair(0, 1)) to Color(0xFF53B1FD),
    listOf(Pair(1, -1), Pair(0, -1), Pair(0, 0), Pair(0, 1)) to Color(0xFFEDEAE9)
)

operator fun Pair<Int, Int>.plus(pair: Pair<Int, Int>): Pair<Int, Int> =
    Pair(first + pair.first, second + pair.second)

operator fun Pair<Int, Int>.div(pair: Pair<Int, Int>): Pair<Int, Int> =
    Pair(first / pair.first, second / pair.second)

operator fun Pair<Int, Int>.times(pair: Pair<Int, Int>): Pair<Int, Int> =
    Pair(first / pair.first, second * pair.second)

fun <T> MutableList<List<T>>.updates(i: Int, j: Int, item: T): MutableList<List<T>> = apply {
    this[j] = this[j].toMutableList().apply {
        this[i] = item
    }
}


val <T> List<List<T>>.multiSize get() = (firstOrNull()?.size ?: 0) to size

fun TetrisGameBlock.createProjection(blocks: Board): TetrisGameBlock {
    var projection = this
    while (projection.move(0 to 1).isValid(blocks)) {
        projection = projection.move(0 to 1)
    }
    return projection
}


fun <Board> Board.modifyBlocks(block: TetrisGameBlock): Pair<Board, Int> {
    val size = multiSize
    var newBoard = this
    var destroyedRows = 0
    block.coordinates.forEach {
        newBoard = newBoard.update(it.first, it.second, block.color)
    }
    if (newBoard.removeAll { row -> row.all { it != Color.Unspecified}}) {
        destroyedRows = size.second - newBoard.size
        newBoard.addAll(0, (0 until destroyedRows).map { (0 until size.first).map { Color.Unspecified } })
    }
    return newBoard to destroyedRows
}


fun TetrisGameBlock.isValid(blocks: Board): Boolean {
    val size = blocks.multiSize
    return coordinates.none {
        it.first <0 || it.first > size.first -1 || it.second > size.second -1 ||
                blocks[it.second][it.first] != Color.Unspecified
    }
}

fun Direction.toOffset() = when (this) {
    Direction.LEFT -> -1 to 0
    Direction.UP -> 0  to -1
    Direction.RIGHT -> 1 to 0
    Direction.DOWN -> 0 to 1
}

fun SwipeEvent(event: MotionEvent?) {
    when (event?.action) {
        MotionEvent.ACTION_UP -> {}
        MotionEvent.ACTION_DOWN -> {}
        MotionEvent.ACTION_MOVE -> {}
        else -> return false
    }
    true
}