package daw.isel.pt.gomoku.domain

data class Lobby(
    val lobbyId: Int,
    val name: String,
    val opening: Int = 1,
    val variant: Int = 1,
    val boardSize: Int = 15,
    val p1: Int,
    val p2: Int?
)