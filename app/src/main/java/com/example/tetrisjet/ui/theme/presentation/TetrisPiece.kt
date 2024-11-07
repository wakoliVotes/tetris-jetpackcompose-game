package com.example.tetrisjet.ui.theme.presentation

data class TetrisPiece(var x: Int, var y: Int)


// Initial position of the Tetris piece
var currentPiece = TetrisPiece(5, 0)

// Define game board dimensions
val boardWidth = 10
val boardHeight = 20

// Moves the Tetris piece left if within bounds
private fun movePieceLeft() {
    if (currentPiece.x > 0) {
        currentPiece = currentPiece.copy(x = currentPiece.x - 1)
        println("Moved Piece to position: ${currentPiece.x}, ${currentPiece.y}")
        // Trigger a recomposition if needed
    }

}

// Moves the Tetris piece right if within bounds
private fun movePieceRight() {
    if (currentPiece.x < boardWidth - 1) {
        currentPiece = currentPiece.copy(x = currentPiece.x + 1)
        println("Moved Piece to position: ${currentPiece.x}, ${currentPiece.y}")
        // Trigger a recomposition if needed
    }
}

