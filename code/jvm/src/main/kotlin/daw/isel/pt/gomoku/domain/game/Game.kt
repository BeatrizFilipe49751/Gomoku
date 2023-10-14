package daw.isel.pt.gomoku.domain.game

import daw.isel.pt.gomoku.domain.game.PieceColor.BLACK
import java.util.UUID

data class Game(
        val id : UUID,
        val board : Board,
        val name: String,
        val state: GameState,
        val currentTurn: PieceColor = BLACK
) {
        fun play(pieceToPlace: Piece, newPosition: Position): Game {
                check(!board.hasPiece(pieceToPlace)) {"Piece already there"}
                check(currentTurn == pieceToPlace.color)
                val newBoard = board.copy(pieces = board.pieces + pieceToPlace)
                return copy(board = newBoard, currentTurn = currentTurn.switchTurn() )
        }




}