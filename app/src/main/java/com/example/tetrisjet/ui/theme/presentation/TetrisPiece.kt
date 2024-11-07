package com.example.tetrisjet.ui.theme.presentation

data class TetrisPiece(var x: Int, var y: Int)


// Initial position of the Tetris piece
var currentPiece = TetrisPiece(5, 0)

// Define game board dimensions
const val boardWidth = 10
const val boardHeight = 20

// Moves the Tetris piece left if within bounds
fun movePieceLeft() {
    if (currentPiece.x > 0) {
        currentPiece = currentPiece.copy(x = currentPiece.x - 1)
        println("Moved Piece to position: ${currentPiece.x}, ${currentPiece.y}")
        // Trigger a recomposition if needed
    }

}

// Moves the Tetris piece right if within bounds
fun movePieceRight() {
    if (currentPiece.x < boardWidth - 1) {
        currentPiece = currentPiece.copy(x = currentPiece.x + 1)
        println("Moved Piece to position: ${currentPiece.x}, ${currentPiece.y}")
        // Trigger a recomposition if needed
    }
}

// Moves the Tetris piece down if within bounds
fun movePieceDown() {
    if (currentPiece.y < boardHeight - 1) {
        currentPiece = currentPiece.copy(y = currentPiece.y + 1)
        println("Moved Piece to position: ${currentPiece.x}, ${currentPiece.y}")
        // Trigger a recomposition if needed
    }
}
