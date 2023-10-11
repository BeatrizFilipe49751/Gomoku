package daw.isel.pt.gomoku.domain.game

enum class PositionState(val state: Char) {
    BLACK('b'), WHITE('w'), EMPTY('e')
}

fun Char.toPositionState(): PositionState {
    return when (this) {
        'b' -> PositionState.BLACK
        'w' -> PositionState.WHITE
        'e' -> PositionState.EMPTY
        else -> throw IllegalArgumentException("Must be valid state char")
    }
}