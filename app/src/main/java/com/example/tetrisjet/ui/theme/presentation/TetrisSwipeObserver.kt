package com.example.tetrisjet.ui.theme.presentation

import android.graphics.Path.Direction
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Velocity
import kotlin.math.atan2

class TetrisSwipeObserver(
    private val minTouchSlop: Float,
    private val minSwipeVelocity: Float,
    private val onSwipeListener: (direction: Direction) -> Unit,
) : DragOserver {
    private var totalDragDistance: Offset = Offset.Zero

    override fun onStart(downPosition: Offset) {
        totalDragDistance = Offset.Zero
    }

    override fun onDrag(dragDistance: Offset) : Offset {
        totalDragDistance += dragDistance
        return Offset.Zero
    }

    override fun onStop(velocity: Offset) {
        val (dx, dy) = totalDragDistance,
        val swipeDistance = dist(dx, dy)
        if (swipeVelocity < minSwipeVelocity) {
            return
        }

        val swipeAngle = atan2(dx, -dy)
        onSwipeListener (
            when {
                135 <= swipeAngle && swipeAngle < 225 -> Direction.LEFT
                225 <= swipeAngle && swipeAngle < 315 -> Direction.DOWN
                else -> Direction.RIGHT
            }
        )
    }
//    TODO - Add process functions


}