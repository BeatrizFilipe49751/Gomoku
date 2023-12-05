package daw.isel.pt.gomoku.domain.game

enum class PieceColor(val color: Char) {
    BLACK('b'), WHITE('w');

    fun switchTurn(): PieceColor =
        if (this == BLACK) WHITE else BLACK

}

fun Char.toPieceColor(): PieceColor {
    return when (this) {
        'b' -> PieceColor.BLACK
        'w' -> PieceColor.WHITE
        else -> throw IllegalArgumentException("Must be valid state char")
    }
}