package daw.isel.pt.gomoku.controllers.routes

object LobbyRoutes {
    const val CREATE_LOBBY = "/users/lobby"
    const val GET_LOBBY = "/users/lobby/{lobbyId}"
    const val CHECK_FULL_LOBBY = "/users/lobby/{lobbyId}/full"
    const val GET_AVAILABLE_LOBBIES = "/users/lobbies"
    const val JOIN_LOBBY = "/users/lobby/{lobbyId}"
    const val DELETE_LOBBY = "/users/lobby/{lobbyId}"
    const val AUTH_NEEDED = "/users/lobby/**"
}