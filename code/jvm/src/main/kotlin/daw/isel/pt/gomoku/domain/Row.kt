package daw.isel.pt.gomoku.domain

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

fun Int.toRowOrNull(): Row? = Row.rows.find { it.identifier == this }
fun Int.indexToRow() = Row.rows.find { it.index == this }