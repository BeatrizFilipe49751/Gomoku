package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.hypermedia.Siren
import daw.isel.pt.gomoku.controllers.hypermedia.toAuthUserSiren
import daw.isel.pt.gomoku.controllers.hypermedia.toGameSiren
import daw.isel.pt.gomoku.controllers.hypermedia.toLobbySiren
import daw.isel.pt.gomoku.controllers.loggin.LoggerMessages
import daw.isel.pt.gomoku.controllers.models.*
import daw.isel.pt.gomoku.controllers.routes.Routes
import daw.isel.pt.gomoku.controllers.utils.*
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.services.GameServices
import daw.isel.pt.gomoku.services.LobbyServices
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class LobbyController(
    private val lobbyServices: LobbyServices,
    private val gameServices: GameServices,
) {
    @PostMapping(Routes.LobbyRoutes.CREATE_LOBBY)
    fun createLobby(
        authUser : AuthUser,
        @RequestBody lobbyIn: LobbyIn
    ): ResponseEntity<Siren<LobbyOut>> {
        logger.info(LoggerMessages.LobbyLoggerMessages.CREATE_LOBBY)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(lobbyServices.createLobby(
                    userId = authUser.user.userId,
                    name = lobbyIn.name,
                    opening = lobbyIn.opening,
                    variant = lobbyIn.variant,
                    boardSize = lobbyIn.size
                )
                .toLobbyOut()
                .toLobbySiren(authUser)
            )
    }

    @GetMapping(Routes.LobbyRoutes.GET_AVAILABLE_LOBBIES)
    fun getLobbies(
        @RequestParam(name = "skip", defaultValue = "0") skip: Int,
        @RequestParam(name = "limit", defaultValue = "5") limit: Int
    ): ResponseEntity<ListOut<Lobby>> {
        logger.info(LoggerMessages.LobbyLoggerMessages.GET_AVAILABLE_LOBBIES)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobbies(
                    skip = skip,
                    limit = limit
                )
            )
    }

    @GetMapping(Routes.LobbyRoutes.GET_LOBBY)
    fun getLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<Siren<LobbyOut>> {
        logger.info(LoggerMessages.LobbyLoggerMessages.GET_LOBBY)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobby(
                    lobbyId = lobbyId
                )
                .toLobbyOut()
                .toLobbySiren(authUser = authUser)
            )
    }

    @GetMapping(Routes.LobbyRoutes.GET_LOBBY_USER_ID)
    fun getLobbyByUserId(
        authUser: AuthUser,
    ): ResponseEntity<Siren<LobbyOut>> {
        logger.info(LoggerMessages.LobbyLoggerMessages.GET_LOBBY_BY_USER_ID)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobbyByUserId(
                authUser.user.userId
            )
                .toLobbyOut()
                .toLobbySiren(authUser = authUser)
            )
    }

    @PutMapping(Routes.LobbyRoutes.JOIN_LOBBY)
    fun joinLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<Siren<GameOut>> {
        logger.info(LoggerMessages.LobbyLoggerMessages.JOIN_LOBBY)

        val user = authUser.user
        val lobby = lobbyServices.getLobby(
            lobbyId = lobbyId
        )

        lobbyServices.joinLobby(
            userId = user.userId,
            lobbyId = lobby.lobbyId
        )
        val newGame = gameServices.createGame(
            name = lobby.name,
            playerBlack = lobby.p1,
            opening= lobby.opening,
            variant = lobby.variant,
            gameNumber = lobbyId,
            playerWhite = user.userId,
            boardSize = lobby.boardSize
        )

        logger.info(newGame.game.gameString())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                newGame.toGameOut()
                    .toGameSiren()
            )
    }

    @DeleteMapping(Routes.LobbyRoutes.QUIT_LOBBY)
    fun quitLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<Siren<AuthUser>> {
        logger.info(LoggerMessages.LobbyLoggerMessages.QUIT_LOBBY)

        val user = authUser.user
        lobbyServices.deleteLobby(
            userId = user.userId,
            lobbyId = lobbyId
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                authUser.toAuthUserSiren()
            )
    }
    @GetMapping(Routes.LobbyRoutes.CHECK_FULL_LOBBY)
    fun checkFullLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<LobbyInfo> {
        logger.info(LoggerMessages.LobbyLoggerMessages.CHECK_FULL_LOBBY)

        val user = authUser.user
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                lobbyServices.checkFullLobby(
                    userId = user.userId,
                    lobbyId = lobbyId
                ).toLobbyInfo()
        )
    }

    companion object{
        private val logger = LoggerFactory
            .getLogger(LobbyController::class.java)
    }
}