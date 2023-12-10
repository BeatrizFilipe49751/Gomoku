package daw.isel.pt.gomoku.services.exceptions

object GameErrorMessages {
    const val GAME_NOT_FOUND = "Game not found"
    const val NOT_YOUR_TURN = "Not your turn"
    const val GAME_FINISHED = "Game has finished"
    const val GAME_DRAW = "Game has finished with a draw"
    const val GAME_CANCELLED = "Game has been canceled"
    const val INVALID_PLAY = "Piece already in that position"
    const val INVALID_GAME_VIEWING = "You cant check an unfinished game"
    const val  GAME_CREATION_ERROR = "Game creation error"
    const val UNKNOWN = "Something is not quite right..."
    fun indexOutOfBoundsMessage(lastIndex: Int) = "Index must be between 0 and $lastIndex"
}