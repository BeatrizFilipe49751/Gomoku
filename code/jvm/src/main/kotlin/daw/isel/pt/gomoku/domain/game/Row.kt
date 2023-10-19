package daw.isel.pt.gomoku.domain.game

data class Row(val identifier: Int) {
    val index = BOARD_DIM - identifier

    companion object {
        val rows = (0 until BOARD_DIM).map { idx -> Row(BOARD_DIM - idx) }
        operator fun invoke(id: Int): Row {
            require(id in 1..BOARD_DIM)
            val idx = id - 1
            return rows[idx - 1]
        }
    }
}

fun Int.indexToRow() = Row.rows.find { it.index == this }
    ?: throw IndexOutOfBoundsException("Index must be between 0 and $LAST_INDEX")