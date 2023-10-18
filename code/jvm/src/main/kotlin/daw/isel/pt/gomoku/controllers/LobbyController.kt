package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.models.GameOut
import daw.isel.pt.gomoku.controllers.models.LobbyIn
import daw.isel.pt.gomoku.controllers.routes.LobbyRoutes
import daw.isel.pt.gomoku.controllers.utils.gameString
import daw.isel.pt.gomoku.controllers.utils.toGameOut
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
class LobbyController(private val lobbyServices: LobbyServices, private val gameServices: GameServices) {
    @PostMapping(LobbyRoutes.CREATE_LOBBY)
    fun createLobby(@PathVariable userId: Int, @RequestBody lobbyIn: LobbyIn): ResponseEntity<Lobby> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(lobbyServices.createLobby(
                    userId = userId,
                    name = lobbyIn.name
                )
            )
    }

    @GetMapping(LobbyRoutes.GET_AVAILABLE_LOBBIES)
    fun getLobbies(@PathVariable userId: Int): ResponseEntity<List<Lobby>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobbies())
    }

    @GetMapping(LobbyRoutes.GET_LOBBY)
    fun getLobby(@PathVariable userId: Int, @PathVariable lobbyId: Int): ResponseEntity<Lobby> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.getLobby(
                    lobbyId = lobbyId
                )
            )
    }

    @PutMapping(LobbyRoutes.JOIN_LOBBY)
    fun joinLobby(@PathVariable userId: Int, @PathVariable lobbyId: Int): ResponseEntity<GameOut> {
        val lobby = lobbyServices.getLobby(
            lobbyId = lobbyId
        )
        lobbyServices.joinLobby(
            userId = userId,
            lobbyId = lobby.lobbyId
        )
        val game = gameServices.createGame(
            name = lobby.name,
            playerBlack = lobby.p1,
            playerWhite = userId
        )

        logger.info(game.gameString())
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(game.toGameOut())
    }

    @DeleteMapping(LobbyRoutes.DELETE_LOBBY)
    fun quitLobby(@PathVariable userId: Int, @PathVariable lobbyId: Int): ResponseEntity<Boolean> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.deleteLobby(
                    userId = userId,
                    lobbyId = lobbyId
                )
            )
    }

    companion object{
        private val logger = LoggerFactory.getLogger(LobbyController::class.java)
    }
}