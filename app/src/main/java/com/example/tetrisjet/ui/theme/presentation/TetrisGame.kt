package com.example.tetrisjet.ui.theme.presentation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.tetrisjet.ui.theme.game.GameViewModel
// TODO - Add dependencies - 28/10/2024
import androidx.compose.ui.gesture.TouchStop
import androidx.compose.ui.gesture.MinFlingVelocity
import kotlinx.coroutines.channels.ticker


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
    val tickerChannel = remember { ticker(delayMillis =  = 300 / state.velocity) }

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

LaunchedTask {
    for (event in tickerChannel) {
        viewModel.consumer(Intent.GameTick)
    }
}

Box(M)