package com.example.tetrisjet.ui.theme.presentation

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import java.lang.reflect.Modifier

class GameSwipeObserver(
    private val minTouchSlop: Float,
    private val minSwipeVelocity: Float,
    private val onSwipeListener: (direction: Path.Direction) -> Unit,
) {
    private var totalDragDistance: Offset = Offset.Zero

    fun onStart(downPosition: Offset) {
        totalDragDistance = Offset.Zero
    }
//    Add onDrag and onStop functions - 01/11/2024

fun onDrag(dragDistance: Offset) : Offset {
        totalDragDistance += dragDistance
        return Offset.Zero
}

fun onStop(velocity: Offset) {
    val (dx, dy) = totalDragDistance
    val swipeDistance = dist(dx, dy)
    if (swipeDistance < minTouchSlop) return

    val (vx, vy) = velocity
    val swipeVelocity = dist(vx, vy)

    if (swipeVelocity < minSwipeVelocity)  return

    val swipeAngle = atan2(dx, -dy)
    onSwipeListener(
        when {
            135 <= swipeAngle && swipeAngle < 225 -> Direction.LEFT
                225 <= swipeAngle && swipeAngle < 315 -> Direction.DOWN
                else -> Direction.RIGHT
        }
    )

        private fun dist(x: Float, y: Float): Float = sqrt(x * x + y * y)

    private fun atan2(x: Float, y: Float): Float {
        var degrees = Math.toDegrees(atan2(y, x).toDouble()).toFloat()
        if (degrees < 0) degrees += 360
        return degrees
    }

    fun Modifier.detectSwipeGestures(
    minTouchSlop: Float,
    minSwipeVelocity: Float,
    onSwipe: (direction: Direction) -> Unit
): Modifier = pointerInput (Unit) {
    val swipeObserver = TetrisSwipeObserver(minTouchSlop, minSwipeVelocity, onSwipe)
    
    detectDragGestures(
        onDragStart = { offset -> swipeObserver.onStart(offset) },
        onDrag = { _, dragAmount -> swipeObserver.onDrag(dragAmount) },
        onDragEnd = { /* Provide velocity if needed, here assuming Offset.Zero */ swipeObserver.onStop(Offset.Zero) },
        onDragCancel = { /* Handle drag cancellation if needed */ }
    )


}

}

// TEST

Box() {
    direction -> 
    // Handle swipe direction
    println("Swipe in direction: $direction" )
}