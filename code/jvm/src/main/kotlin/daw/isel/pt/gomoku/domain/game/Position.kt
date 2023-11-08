package daw.isel.pt.gomoku.domain.game


data class Position(val row: Row, val column: Column){
    companion object {
        fun deserialize(pos: String, boardDim: Int): Position {
            val params = pos.split(":")
            check(params.size == 2) {"Position Parameter size wrong"}
            check(params[0].all { it.isDigit() }) {"Row index is not a number"}
            check(params[1].all { it.isDigit() }) {"Column index is not a number"}
            return Position(
                params[0].toInt().indexToRow(boardDim),
                params[1].toInt().indexToColumn(boardDim)
            )
        }

        private fun grid(bd: Int) = (0 until (bd*bd)).map{
                idx -> Position((idx / bd).indexToRow(bd), (idx % bd).indexToColumn(bd), bd)
        }

        operator fun invoke(r: Int, c: Int, bd: Int): Position {
            require(r in 0 until bd) { "Illegal coordinates must be between 0 and ${bd - 1}" }
            require(c in 0 until bd) { "Illegal coordinates must be between 0 and ${bd - 1}" }
            val index = r * bd + c
            return grid(bd)[index]
        }
        operator fun invoke(r: Row, c: Column, bd: Int): Position {
            require(r.index in 0 until bd) { "Illegal coordinates must be between 0 and ${bd - 1}" }
            require(c.index in 0 until bd) { "Illegal coordinates must be between 0 and ${bd - 1}" }
            val index = r.index * bd + c.index
            return grid(bd)[index]
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