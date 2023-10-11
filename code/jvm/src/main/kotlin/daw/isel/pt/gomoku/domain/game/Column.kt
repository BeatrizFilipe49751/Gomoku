package daw.isel.pt.gomoku.domain.game

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

fun Char.toColumnOrNull() =
    Column.columns.find { it.symbol == this }


fun Int.indexToColumn() = Column.columns.find { it.index == this }
    ?: throw IndexOutOfBoundsException("Index must be between 0 and $BOARD_DIM")
