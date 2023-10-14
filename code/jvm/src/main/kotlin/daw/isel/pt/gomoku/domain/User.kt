package daw.isel.pt.gomoku.domain

data class User (
        val userId: Int,
        val username: String,
        val email: String,
        val token: String
)