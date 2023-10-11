package daw.isel.pt.gomoku.domain.game

data class Position(val row: Row, val column: Column, val state: PositionState){
    companion object {
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
}

fun String.toPositionOrNull(): Position? {
    if ( !this[0].isDigit() ) return null
    val aux = this.partition { it.isDigit() }
    return Position.positions
        .find { it.row.identifier == aux.first.toInt() && it.column.symbol == aux.second[0] }
}