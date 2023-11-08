package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.hypermedia.Siren
import daw.isel.pt.gomoku.controllers.hypermedia.toGameSiren
import daw.isel.pt.gomoku.controllers.hypermedia.toLobbySiren
import daw.isel.pt.gomoku.controllers.hypermedia.toAuthUserSiren
import daw.isel.pt.gomoku.controllers.models.GameOut
import daw.isel.pt.gomoku.controllers.models.LobbyIn
import daw.isel.pt.gomoku.controllers.models.LobbyInfo
import daw.isel.pt.gomoku.controllers.models.LobbyOut
import daw.isel.pt.gomoku.controllers.routes.LobbyRoutes
import daw.isel.pt.gomoku.controllers.utils.*
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.services.GameServices
import daw.isel.pt.gomoku.services.LobbyServices
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LobbyController(
    private val lobbyServices: LobbyServices,
    private val gameServices: GameServices,
) {
    @PostMapping(LobbyRoutes.CREATE_LOBBY)
    fun createLobby(
        authUser : AuthUser,
        @RequestBody lobbyIn: LobbyIn
    ): ResponseEntity<Siren<LobbyOut>> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(lobbyServices.createLobby(
                    userId = authUser.user.userId,
                    name = lobbyIn.name
                )
                .toLobbyOut()
                .toLobbySiren(authUser)
            )
    }

    @GetMapping(LobbyRoutes.GET_AVAILABLE_LOBBIES)
    fun getLobbies(): ResponseEntity<List<Lobby>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobbies())
    }

    @GetMapping(LobbyRoutes.GET_LOBBY)
    fun getLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<Siren<LobbyOut>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobby(
                    lobbyId = lobbyId
                )
                .toLobbyOut()
                .toLobbySiren(authUser = authUser)
            )
    }

    @PutMapping(LobbyRoutes.JOIN_LOBBY)
    fun joinLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<Siren<GameOut>> {
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
            gameNumber = lobbyId,
            playerWhite = user.userId
        )

        logger.info(newGame.game.gameString())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                newGame.toGameOut()
                    .toGameSiren()
            )
    }

    @DeleteMapping(LobbyRoutes.DELETE_LOBBY)
    fun quitLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<Siren<AuthUser>> {
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
    @GetMapping(LobbyRoutes.CHECK_FULL_LOBBY)
    fun checkFullLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<LobbyInfo> {
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
        private val logger = LoggerFactory.getLogger(LobbyController::class.java)
    }
}