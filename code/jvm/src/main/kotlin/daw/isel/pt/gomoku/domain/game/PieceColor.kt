package daw.isel.pt.gomoku.domain.game

enum class PieceColor(val color: Char) {
    BLACK('b'), WHITE('w')
}

fun Char.toPieceColor(): PieceColor {
    return when (this) {
        'b' -> PieceColor.BLACK
        'w' -> PieceColor.WHITE
        else -> throw IllegalArgumentException("Must be valid state char")
    }
}