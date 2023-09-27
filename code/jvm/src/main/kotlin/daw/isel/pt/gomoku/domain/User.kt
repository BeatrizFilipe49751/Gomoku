package daw.isel.pt.gomoku.domain

import java.util.UUID

data class User (
        val id: Int,
        val username: String,
        val token: String
)