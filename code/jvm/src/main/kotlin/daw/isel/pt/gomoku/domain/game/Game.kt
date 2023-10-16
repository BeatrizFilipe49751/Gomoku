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
                check(currentTurn == pieceToPlace.color) { "Not your turn" }
                check(!board.hasPiece(pieceToPlace)) { "Piece already there" }
                val newBoard = board.copy(pieces = board.pieces + pieceToPlace)
                if (newBoard.pieces.size < WIN_STREAK)
                        return copy(board = newBoard, currentTurn = currentTurn.switchTurn() )
                return if (checkWin(newBoard, pieceToPlace))
                        copy(board = newBoard, state = FINISHED)
                else copy(board = newBoard, currentTurn = currentTurn.switchTurn() )
        }

        private fun checkWin(board: Board, p: Piece): Boolean {
                val neighbors = MutableList(DIRECTIONS) { 0 }
                for (i in 1 until WIN_STREAK) {
                        Position.DirectionMath.values().forEachIndexed { index, element ->
                                val pair = element.pair
                                val currentCheck =
                                        board.positionHasColoredPiece(
                                                p.position.row.index + (i * pair.first) ,
                                                p.position.column.index + (i * pair.second),
                                                p.color
                                        )
                                if (currentCheck)
                                        neighbors[index]++
                                if (neighbors[index] == WIN_STREAK - 1)
                                        return true
                        }
                }
                return false
        }
}