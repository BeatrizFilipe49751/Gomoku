package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.models.GameOut
import daw.isel.pt.gomoku.controllers.models.PlayIn
import daw.isel.pt.gomoku.controllers.routes.GameRoutes
import daw.isel.pt.gomoku.controllers.utils.toGameOut
import daw.isel.pt.gomoku.services.GameServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(val gameServices: GameServices) {
    @PutMapping(GameRoutes.PLAY)
    fun play(@RequestBody playIn: PlayIn, @PathVariable userId: String): ResponseEntity<GameOut> {
        val game = gameServices.getGame(playIn.gameId)
        val newGame = gameServices.play(game, playIn.row, playIn.col)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(newGame.toGameOut())
    }
}