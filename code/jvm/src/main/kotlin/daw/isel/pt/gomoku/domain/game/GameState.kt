package daw.isel.pt.gomoku.domain.game

enum class GameState(val stateChar: Char) {
    ACTIVE('A'),
    FINISHED('F')
}

fun Char.toGameState(): GameState {
    return when(this){
        'A' -> GameState.ACTIVE
        'F' -> GameState.FINISHED
        else -> throw IllegalArgumentException("Invalid char for gameState")
    }
}