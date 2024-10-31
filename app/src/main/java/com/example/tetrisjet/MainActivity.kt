package com.example.tetrisjet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tetrisjet.ui.theme.TetrisjetTheme
import com.example.tetrisjet.ui.theme.presentation.TetrisGame

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TetrisjetTheme {
                Surface (color = MaterialTheme.colorScheme.background) {
                    TetrisGame()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TetrisGamePreview() {
    TetrisjetTheme {
        TetrisGame()
    }
}