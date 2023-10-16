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

    enum class DirectionMath(val pair: Pair<Int, Int>) {
        UP(Pair(0, 1)),
        DOWN(Pair(0, -1)),
        LEFT(Pair(-1, 0)),
        RIGHT(Pair(1, 0)),
        UP_LEFT(Pair(-1, 1)),
        UP_RIGHT(Pair(1, 1)),
        DOWN_LEFT(Pair(-1, -1)),
        DOWN_RIGHT(Pair(1, -1))
    }

    override fun toString() = "${row.index}:${column.index}"
}