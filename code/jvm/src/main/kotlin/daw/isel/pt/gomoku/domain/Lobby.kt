package daw.isel.pt.gomoku.domain

data class Lobby(
    val lobbyId: Int,
    val name: String,
    val opening: Int,
    val variant: Int,
    val p1: Int,
    val p2: Int?
)