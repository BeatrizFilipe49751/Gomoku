package daw.isel.pt.gomoku.domain

import java.util.*

data class Game(
        val id : UUID,
        val board : Board,
        val name: String,
        val playerBlack : Int,
        val playerWhite : Int,
        val state: GameState
)