package daw.isel.pt.gomoku.services.exceptions

import daw.isel.pt.gomoku.domain.game.LAST_INDEX

object GameErrorMessages {
    const val GAME_NOT_FOUND = "Game not found"
    const val NOT_YOUR_TURN = "Not your turn"
    const val GAME_FINISHED = "Game has finished"
    const val INVALID_PLAY = "Piece already in that position"
    const val INVALID_GAME_VIEWING = "You cant check an unfinished game"
    const val  GAME_CREATION_ERROR = "Game creation error"
    const val INDEX_OUT_OF_BOUNDS = "Index must be between 0 and $LAST_INDEX"
}