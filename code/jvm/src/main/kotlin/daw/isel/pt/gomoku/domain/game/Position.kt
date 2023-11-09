package daw.isel.pt.gomoku.domain.game


data class Position(val column: Column, val row: Row){
    companion object {
        fun deserialize(pos: String, boardDim: Int): Position {
            val params = pos.split(":")
            check(params.size == 2) {"Position Parameter size wrong"}
            check(params[0].all { it.isDigit() }) {"Column index is not a number"}
            check(params[1].all { it.isDigit() }) {"Row index is not a number"}
            return Position(
                params[0].toInt().indexToColumn(boardDim),
                params[1].toInt().indexToRow(boardDim),
            )
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

    override fun toString() = "${column.index}:${row.index}"
}