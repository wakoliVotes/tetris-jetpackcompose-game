package com.example.tetrisjet.ui.theme.presentation

import android.graphics.Path
import androidx.compose.ui.geometry.Offset

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

}