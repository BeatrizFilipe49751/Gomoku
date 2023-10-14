package daw.isel.pt.gomoku.controllers.routes

object UserRoutes {
    const val GET_USER = "/users/{userId}"
    const val CREATE_USER="/users"
    const val CREATE_LOBBY = "/users/{userId}/lobby"
    const val GET_AVAILABLE_LOBBIES = "/users/{userId}/lobbies"
    const val JOIN_LOBBY = "/users/{userId}/lobby/{lobbyId}"
    const val AUTH_NEEDED = "/users/{userId}/lobby/**"
}