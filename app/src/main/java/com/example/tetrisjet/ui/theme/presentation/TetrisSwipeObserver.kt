package com.example.tetrisjet.ui.theme.presentation

import android.graphics.Path.Direction
import androidx.compose.ui.geometry.Offset
import kotlin.math.sqrt
import androidx.compose.foundation.gestures.DragScope
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown

class TetrisSwipeObserver(
    private val minTouchSlop: Float,
    private val minSwipeVelocity: Float,
    private val onSwipeListener: (direction: Direction) -> Unit,
) : DragScope {
    private var totalDragDistance: Offset = Offset.Zero

    override fun dragBy(pixels: Float) {
        TODO("Not yet implemented")
    }

    override fun onStart(downPosition: Offset) {
        totalDragDistance = Offset.Zero
    }

    override fun onDrag(dragDistance: Offset) : Offset {
        totalDragDistance += dragDistance
        return Offset.Zero
    }

    override fun onStop(velocity: Offset) {
        val (dx, dy) = totalDragDistance
        val swipeDistance = dist(dx, dy)
        if (swipeDistance < minTouchSlop) {
            return
        }

        val (vx, vy) = velocity
        val swipeVelocity = dist(vx, vy)
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
//    TODO - Add dist and atan2 functions - 28/10/2024
    private fun dist(x: Float, y: Float): Float {
        return sqrt(x * x * y * y)
    }

    private fun atan2(x: Float, y: Float): Float {
        var degrees = Math.toDegrees(kotlin.math.atan2(y, x).toDouble().toFloat().toDouble())
        if (degrees < 0) {
            degrees += 360
        }
        return degrees.toFloat()
    }
}