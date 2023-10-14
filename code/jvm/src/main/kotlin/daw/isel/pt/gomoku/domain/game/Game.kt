package daw.isel.pt.gomoku.domain.game

import daw.isel.pt.gomoku.domain.game.GameState.*
import daw.isel.pt.gomoku.domain.game.PieceColor.BLACK
import java.util.UUID

data class Game(
        val id : UUID,
        val board : Board,
        val name: String,
        val state: GameState,
        val currentTurn: PieceColor = BLACK
) {
        fun play(pieceToPlace: Piece): Game {
                check(state == ACTIVE) { "Game has finished" }
                check(currentTurn == pieceToPlace.color)
                check(!board.positionHasPiece(pieceToPlace)) { "Piece already there" }
                val newBoard = board.copy(pieces = board.pieces + pieceToPlace)
                return if (checkWin(newBoard, pieceToPlace))
                        copy(board = newBoard, state = FINISHED)
                else copy(board = newBoard, currentTurn = currentTurn.switchTurn() )
        }

        private fun checkWin(board: Board, piece: Piece): Boolean {
                val directions = piece.position.Directions().getDirections()
                if (directions.isNotEmpty()) {
                        val foundPieces = directions.map { direction ->
                                val filteredDir = direction.filter { pos ->
                                        pos in board.pieces
                                                .filter { it.color == piece.color}
                                                .map { it.position }
                                }
                                if (filteredDir.size >= WIN_STREAK - 1 )
                                        return true
                        }

                }
                return false
        }
}