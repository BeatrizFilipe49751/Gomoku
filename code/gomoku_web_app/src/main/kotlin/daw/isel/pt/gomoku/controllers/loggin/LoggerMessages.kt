package daw.isel.pt.gomoku.controllers.loggin

object LoggerMessages {
    object GameLoggerMessages {
        const val PLAY = "Handling Game: PLAY"
        const val GET_GAME= "Handling Game: GET GAME"
        const val GET_USER_ACTIVE_GAME = "Handling Game: GET USER ACTIVE GAME"
    }

    object UserLoggerMessages {
        const val GET_USER = "Handling User: GET USER"
        const val CREATE_USER= "Handling User: CREATE USER"
        const val LOGIN = "Handling User: LOGIN"
        const val LOGOUT = "Handling User: LOGOUT"
        const val GET_LEADERBOARD = "Handling User: GET LEADERBOARD"
    }

    object AuthLoggerMessages {
        const val AUTH_INTERCEPTOR = "Handling AUTH INTERCEPTOR"
    }
    object LobbyLoggerMessages {
        const val CREATE_LOBBY = "Handling Lobby: CREATE LOBBY"
        const val GET_LOBBY = "Handling Lobby: GET LOBBY"
        const val GET_LOBBY_BY_USER_ID = "Handling Lobby: GET BY USER ID LOBBY"
        const val CHECK_FULL_LOBBY = "Handling Lobby: CHECK FULL LOBBY"
        const val GET_AVAILABLE_LOBBIES = "Handling Lobby: GET AVAILABLE LOBBIES"
        const val JOIN_LOBBY = "Handling Lobby: JOIN LOBBY"
        const val QUIT_LOBBY = "Handling Lobby: QUIT LOBBY"
    }
}