package daw.isel.pt.gomoku.controllers.routes

object Routes {

    const val PREFIX = "/api"
    object GameRoutes {
        const val PLAY = "$PREFIX/users/game/play/{gameId}"
        const val GET_GAME= "$PREFIX/users/game/{gameId}"
        const val GET_USER_ACTIVE_GAME= "$PREFIX/users/game"
        const val AUTH_NEEDED = "$PREFIX/users/game/**"
    }

    object UserRoutes {
        const val GET_USER = "$PREFIX/users/{userId}"
        const val CREATE_USER= "$PREFIX/users"
        const val LOGIN = "$PREFIX/users/login"
        const val LOGOUT = "$PREFIX/users/logout"
        const val GET_LEADERBOARD = "$PREFIX/users/leaderboard"
    }

    object LobbyRoutes {
        const val CREATE_LOBBY = "$PREFIX/users/lobby"
        const val GET_LOBBY = "$PREFIX/users/lobby/{lobbyId}"
        const val GET_LOBBY_USER_ID = "$PREFIX/users/lobby/get"
        const val CHECK_FULL_LOBBY = "$PREFIX/users/lobby/{lobbyId}/full"
        const val GET_AVAILABLE_LOBBIES = "$PREFIX/users/lobbies"
        const val JOIN_LOBBY = "$PREFIX/users/lobby/{lobbyId}"
        const val QUIT_LOBBY = "$PREFIX/users/lobby/{lobbyId}"
        const val AUTH_NEEDED = "$PREFIX/users/lobby/**"
    }
}