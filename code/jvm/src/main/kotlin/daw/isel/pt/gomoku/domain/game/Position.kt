package daw.isel.pt.gomoku.domain.game


data class Position(val row: Row, val column: Column, val state: PositionState){
    companion object {
        fun deserialize(pos: String): Position {
            val params = pos.split(":")
            check(params.size == 3) {"Parameter size wrong"}
            check(params[0].all { it.isDigit() }) {"Row index is not a number"}
            check(params[1].all { it.isDigit() }) {"Column index is not a number"}
            check(params[2].length == 1) {"Position State must be a single char"}
            return Position(
                params[0].toInt().indexToRow(),
                params[1].toInt().indexToColumn(),
                params[2].first().toPositionState()
            )
        }

        val positions = (0 until MAX_POSITIONS).map{
            idx -> Position((idx / BOARD_DIM).indexToRow(), (idx % BOARD_DIM).indexToColumn(), PositionState.EMPTY)
        }

        operator fun invoke(r: Int, c: Int): Position {
            require(r in 0 until BOARD_DIM) { "Illegal coordinates must be between 0 and $LAST_INDEX" }
            require(c in 0 until BOARD_DIM) { "Illegal coordinates must be between 0 and $LAST_INDEX" }
            val index = r * BOARD_DIM + c
            return positions[index]
        }
        operator fun invoke(r: Row, c: Column): Position {
            require(r.index in 0 until BOARD_DIM) { "Illegal coordinates must be between 0 and $LAST_INDEX" }
            require(c.index in 0 until BOARD_DIM) { "Illegal coordinates must be between 0 and $LAST_INDEX" }
            val index = r.index * BOARD_DIM + c.index
            return positions[index]
        }
    }

    override fun toString() = "${row.index}:${column.index}:${state.state}"
}