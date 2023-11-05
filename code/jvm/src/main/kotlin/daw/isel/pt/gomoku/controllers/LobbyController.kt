package daw.isel.pt.gomoku.controllers

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
import daw.isel.pt.gomoku.services.UserServices
import jakarta.servlet.http.HttpServletRequest
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
    private val userServices: UserServices
) {
    @PostMapping(LobbyRoutes.CREATE_LOBBY)
    fun createLobby(
        authUser : AuthUser,
        @RequestBody lobbyIn: LobbyIn
    ): ResponseEntity<LobbyOut> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(lobbyServices.createLobby(
                    userId = authUser.user.userId,
                    name = lobbyIn.name
                ).toLobbyOut()
            )
    }

    @GetMapping(LobbyRoutes.GET_AVAILABLE_LOBBIES)
    fun getLobbies(): ResponseEntity<List<Lobby>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobbies())
    }

    @GetMapping(LobbyRoutes.GET_LOBBY)
    fun getLobby(@PathVariable lobbyId: Int): ResponseEntity<Lobby> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobby(
                    lobbyId = lobbyId
                )
            )
    }

    @PutMapping(LobbyRoutes.JOIN_LOBBY)
    fun joinLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<GameOut> {
        val user = authUser.user
        val lobby = lobbyServices.getLobby(
            lobbyId = lobbyId
        )

        lobbyServices.joinLobby(
            userId = user.userId,
            lobbyId = lobby.lobbyId
        )
        val game = gameServices.createGame(
            name = lobby.name,
            playerBlack = lobby.p1,
            gameNumber = lobbyId,
            playerWhite = user.userId
        )

        logger.info(game.gameString())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(game.toGameOut())
    }

    @DeleteMapping(LobbyRoutes.DELETE_LOBBY)
    fun quitLobby(
        authUser: AuthUser,
        @PathVariable lobbyId: Int
    ): ResponseEntity<Lobby> {
        val user = authUser.user
        lobbyServices.deleteLobby(
            userId = user.userId,
            lobbyId = lobbyId
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                lobbyServices.getLobby(lobbyId)
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