package daw.isel.pt.gomoku.domain.game


const val BOARD_DIM = 15
const val LAST_INDEX = BOARD_DIM - 1
const val LAST_COL_CHAR = 'a' + LAST_INDEX
const val MAX_POSITIONS = BOARD_DIM * BOARD_DIM
data class Board(val id: Int, val positions : List<Position> = Position.positions) {

    override fun toString() = "$id\n" + positions.joinToString { it.toString() + "\n" }

    companion object {
        fun deserialize(b: String): Board {
            val params = b.split("\n")
            check(params.size == 1 + MAX_POSITIONS) // id + positions
            val idString = params[0]
            check(idString.all { it.isDigit() })
            val positions = params.drop(1).map { Position.deserialize(it) }
            return Board(idString.toInt(), positions)
        }
    }

    //TODO: Game Logic
}