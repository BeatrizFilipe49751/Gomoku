package daw.isel.pt.gomoku.controllers.routes

object LobbyRoutes {
    const val CREATE_LOBBY = "/users/{userId}/lobby"
    const val GET_LOBBY = "/users/{userId}/lobby/{lobbyId}"
    const val CHECK_FULL_LOBBY = "/users/{userId}/lobby/{lobbyId}/full"
    const val GET_AVAILABLE_LOBBIES = "/users/{userId}/lobbies"
    const val JOIN_LOBBY = "/users/{userId}/lobby/{lobbyId}"
    const val DELETE_LOBBY = "/users/{userId}/lobby/{lobbyId}"
}