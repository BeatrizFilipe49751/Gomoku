package daw.isel.pt.gomoku.domain.game

import daw.isel.pt.gomoku.services.exceptions.GameError
import daw.isel.pt.gomoku.services.exceptions.GameErrorMessages

data class Column(val symbol: Char) {
    val index: Int = symbol - 'a'

    companion object {
        val columns = ('a' .. LAST_COL_CHAR).map { idx -> Column(idx) }
        operator fun invoke(s: Char): Column {
            require(s in 'a' .. LAST_COL_CHAR) { "Symbol must be between 'a' and '$LAST_COL_CHAR'" }
            val index = s - 'a'
            return columns[index]
        }
    }
}

fun Int.indexToColumn() = Column.columns.find { it.index == this }
    ?: throw GameError(GameErrorMessages.INDEX_OUT_OF_BOUNDS)
