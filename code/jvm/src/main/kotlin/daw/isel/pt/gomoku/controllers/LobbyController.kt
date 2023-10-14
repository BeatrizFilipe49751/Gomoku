package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.routes.LobbyRoutes
import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.services.LobbyServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LobbyController(private val lobbyServices: LobbyServices) {
    @PostMapping(LobbyRoutes.CREATE_LOBBY)
    fun createLobby(@PathVariable userId: Int): ResponseEntity<Int> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(lobbyServices.createLobby(userId))
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
            .body(lobbyServices.getLobby(userId, lobbyId))
    }

    @PutMapping(LobbyRoutes.JOIN_LOBBY)
    fun joinLobby(@PathVariable userId: Int, @PathVariable lobbyId: Int): ResponseEntity<Boolean> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(lobbyServices.joinLobby(userId, lobbyId))
    }
}