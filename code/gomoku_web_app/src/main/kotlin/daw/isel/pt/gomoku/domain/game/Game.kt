package daw.isel.pt.gomoku.domain.game

import daw.isel.pt.gomoku.domain.game.GameState.*
import daw.isel.pt.gomoku.domain.game.PieceColor.BLACK
import daw.isel.pt.gomoku.services.exceptions.GameErrorMessages

const val winPoints = 15
val bonusPoints get() = (0..10).random()

data class Game(
        val id : String,
        val board : Board,
        val name: String,
        val state: GameState = ACTIVE,
        val opening: Opening = Opening.FREESTYLE,
        val variant: Variant = Variant.FREESTYLE,
        val currentTurn: PieceColor = BLACK
) {
        fun play(pieceToPlace: Piece): Game {
                check(state == ACTIVE) {
                        when (state) {
                                FINISHED -> GameErrorMessages.GAME_FINISHED
                                DRAW -> GameErrorMessages.GAME_DRAW
                                CANCELLED -> GameErrorMessages.GAME_CANCELLED
                                else -> GameErrorMessages.UNKNOWN
                        }
                }
                check(!board.hasPiece(pieceToPlace)) { GameErrorMessages.INVALID_PLAY }
                val newBoard = board.copy(pieces = board.pieces + pieceToPlace)
                if (newBoard.pieces.size < (WIN_STREAK*2) - 1)
                        return copy(board = newBoard, currentTurn = currentTurn.switchTurn() )
                return if (checkWin(newBoard, pieceToPlace))
                        copy(board = newBoard, state = FINISHED)
                else {
                        if (newBoard.pieces.size == board.maxPieces)
                                copy(board = newBoard, state = DRAW)
                        else
                                copy(board = newBoard, currentTurn = currentTurn.switchTurn())
                }
        }

        private fun checkWin(board: Board, p: Piece): Boolean {
                val neighbors = MutableList(DIRECTIONS) { 0 }
                for (i in 1 until WIN_STREAK) {
                        Position.DirectionMath.values().forEachIndexed { index, element ->
                                val pair = element.pair
                                val currentCheck =
                                        board.positionHasColoredPiece(
                                                p.position.column.index + (i * pair.second),
                                                p.position.row.index + (i * pair.first) ,
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