package daw.isel.pt.gomoku.domain.game

import daw.isel.pt.gomoku.services.exceptions.GameError
import daw.isel.pt.gomoku.services.exceptions.GameErrorMessages

data class Column(val symbol: Char) {
    val index: Int = symbol - 'a'

    companion object {
        fun columns(boardDim: Int) = ('a' .. 'a' + (boardDim - 1)).map { idx -> Column(idx) }
    }
}

fun Int.indexToColumn(boardDim: Int) = Column.columns(boardDim).find { it.index == this }
    ?: throw GameError(GameErrorMessages.indexOutOfBoundsMessage(boardDim))
