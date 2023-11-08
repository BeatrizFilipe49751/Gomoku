package daw.isel.pt.gomoku.domain.game

import daw.isel.pt.gomoku.services.exceptions.GameError
import daw.isel.pt.gomoku.services.exceptions.GameErrorMessages

data class Row(val identifier: Int, val boardDim: Int) {
    val index = boardDim - identifier
    companion object {
        fun rows(boardDim: Int) = (0 until boardDim).map { idx -> Row(boardDim - idx, boardDim) }
    }
}

fun Int.indexToRow(boardDim: Int) = Row.rows(boardDim).find { it.index == this }
    ?: throw GameError(GameErrorMessages.indexOutOfBoundsMessage(boardDim - 1))
