package daw.isel.pt.gomoku.domain.game


data class Position(val row: Row, val column: Column){
    companion object {
        fun deserialize(pos: String): Position {
            val params = pos.split(":")
            check(params.size == 2) {"Parameter size wrong"}
            check(params[0].all { it.isDigit() }) {"Row index is not a number"}
            check(params[1].all { it.isDigit() }) {"Column index is not a number"}
            return Position(
                params[0].toInt().indexToRow(),
                params[1].toInt().indexToColumn()
            )
        }

        private val grid = (0 until MAX_POSITIONS).map{
            idx -> Position((idx / BOARD_DIM).indexToRow(), (idx % BOARD_DIM).indexToColumn())
        }

        operator fun invoke(r: Int, c: Int): Position {
            require(r in 0 until BOARD_DIM) { "Illegal coordinates must be between 0 and $LAST_INDEX" }
            require(c in 0 until BOARD_DIM) { "Illegal coordinates must be between 0 and $LAST_INDEX" }
            val index = r * BOARD_DIM + c
            return grid[index]
        }
        operator fun invoke(r: Row, c: Column): Position {
            require(r.index in 0 until BOARD_DIM) { "Illegal coordinates must be between 0 and $LAST_INDEX" }
            require(c.index in 0 until BOARD_DIM) { "Illegal coordinates must be between 0 and $LAST_INDEX" }
            val index = r.index * BOARD_DIM + c.index
            return grid[index]
        }
    }

    inner class Directions {
        private val up get() =
            Array(WIN_STREAK - 1) {Position(row, (column.index + (it + 1)).indexToColumn())}
        private val down get() =
            Array(WIN_STREAK - 1) {Position(row, (column.index - (it + 1)).indexToColumn())}
        private val left get() =
            Array(WIN_STREAK - 1) {Position((row.index - (it + 1)).indexToRow(), column)}
        private val right get() =
            Array(WIN_STREAK - 1) {Position((row.index + (it + 1)).indexToRow(), column)}
        private val upLeft get() =
            Array(WIN_STREAK - 1) {
                Position(
                    (row.index - (it + 1)).indexToRow(),
                    (column.index + (it + 1)).indexToColumn()
                )
            }
        private val upRight get() =
            Array(WIN_STREAK - 1) {
                Position(
                    (row.index + (it + 1)).indexToRow(),
                    (column.index + (it + 1)).indexToColumn()
                )
            }
        private val downLeft get() =
            Array(WIN_STREAK - 1) {
                Position(
                    (row.index - (it + 1)).indexToRow(),
                    (column.index - (it + 1)).indexToColumn()
                )
            }
        private val downRight get() =
            Array(WIN_STREAK - 1) {
                Position(
                    (row.index + (it + 1)).indexToRow(),
                    (column.index - (it + 1)).indexToColumn()
                )
            }

        private val directions = arrayOf(
            {up},
            {down},
            {left},
            {right},
            {upLeft},
            {downLeft},
            {upRight},
            {downRight},
        )

        fun getDirections(): List<Array<Position>> {
            val list = mutableListOf<Array<Position>>()
            directions.forEach {
                try {
                    list.add(it())
                } catch (_: Exception) {
                    // if an index is out of bounds,
                    // the direction is not added and the
                    // cycle is continued
                }
            }
            return list
        }
    }

    override fun toString() = "${row.index}:${column.index}"
}