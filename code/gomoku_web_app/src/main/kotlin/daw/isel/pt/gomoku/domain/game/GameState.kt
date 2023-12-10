package daw.isel.pt.gomoku.domain.game

enum class GameState(val stateChar: Char) {
    ACTIVE('A'),
    FINISHED('F'),
    DRAW('D'),
    CANCELLED('C')
}

fun Char.toGameState(): GameState {
    return when(this){
        'A' -> GameState.ACTIVE
        'F' -> GameState.FINISHED
        'D' -> GameState.DRAW
        'C' -> GameState.CANCELLED
        else -> throw IllegalArgumentException("Invalid char for gameState")
    }
}