package daw.isel.pt.gomoku.domain.game

const val BOARD_DIM = 15
const val LAST_INDEX = BOARD_DIM - 1
const val LAST_COL_CHAR = 'a' + LAST_INDEX
const val MAX_POSITIONS = BOARD_DIM * BOARD_DIM
const val WIN_STREAK = 5
const val DIRECTIONS = 8

data class Board(val id: Int, val pieces: List<Piece> = emptyList()) {

    companion object {
        fun deserialize(b: String): Board {
            val params = b.split("\n")
            check(params.isNotEmpty()) // at least must have id
            val idString = params[0]
            check(idString.all { it.isDigit() })
            return Board(
                idString.toInt(),
                if (params.size > 1) params.drop(1).map { Piece.deserialize(it) }
                else emptyList()
            )
        }
    }

    val isEmptyBoard get() = pieces.isEmpty()

    fun hasPiece(p: Piece) = pieces.find { it.position == p.position } != null

    fun positionHasColoredPiece(row: Int, col: Int, color: PieceColor) =
        try {
            val piece = pieces.find { it.position == Position(row, col) }
            piece != null && piece.color == color
        } catch (_: Exception) {
            false
        }

    override fun toString() =
        "$id\n" + if (isEmptyBoard) "" else pieces.joinToString { it.toString() + "\n" }
}