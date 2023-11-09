package daw.isel.pt.gomoku.domain.game

const val BOARD_DIM_MIN = 15
const val BOARD_DIM_MAX = 19
const val WIN_STREAK = 5
const val DIRECTIONS = 8

data class Board(val size: Int = BOARD_DIM_MIN, val pieces: List<Piece> = emptyList()) {

    companion object {
        fun deserialize(b: String): Board {
            val params = b.replace(" ", "").split(",")
            check(params[0].all { it.isDigit() }) {"Board Dimension is not a number"}
            val boardDim = params[0].toInt()
            return if (params.size > 1)
                Board(
                    boardDim,
                    params.drop(1).map { Piece.deserialize(it, boardDim) }
                )
            else Board(boardDim)
        }
    }

    val isEmptyBoard get() = pieces.isEmpty()

    fun hasPiece(p: Piece) = pieces.find { it.position == p.position } != null

    fun positionHasColoredPiece(row: Int, col: Int, color: PieceColor) =
        try {
            val piece = pieces.find { it.position == Position(row.indexToRow(size), col.indexToColumn(size)) }
            piece != null && piece.color == color
        } catch (_: Exception) {
            false
        }

    fun serialize() =
        if (isEmptyBoard) "$size" else "$size, " + pieces.joinToString { it.toString() }
}