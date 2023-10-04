package daw.isel.pt.gomoku.domain

import java.util.*

data class Game(
        val id : UUID,
        val board : Int,
        val name: String,
        val state: GameState
)