package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.hypermedia.Siren
import daw.isel.pt.gomoku.controllers.hypermedia.toGameInfoSiren
import daw.isel.pt.gomoku.controllers.hypermedia.toGameSiren
import daw.isel.pt.gomoku.controllers.models.PlayIn
import daw.isel.pt.gomoku.controllers.routes.GameRoutes
import daw.isel.pt.gomoku.controllers.utils.gameString
import daw.isel.pt.gomoku.controllers.utils.toGameOut
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.services.GameServices
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(val gameServices: GameServices) {
    @PutMapping(GameRoutes.PLAY)
    fun play(
        authUser: AuthUser,
        @PathVariable gameId: String,
        @RequestBody playIn: PlayIn
    ): ResponseEntity<Siren> {
        val user = authUser.user
        val game = gameServices.getGame(
            gameId = gameId
        )
        val newGame = gameServices.play(
            game = game,
            userId = user.userId,
            row = playIn.row,
            col = playIn.col
        )
        logger.info("\n" + newGame.game.gameString())
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                newGame.toGameOut()
                    .toGameSiren()
            )
    }

    @GetMapping(GameRoutes.GET_GAME)
    fun getGame(authUser: AuthUser, @PathVariable gameId: String): ResponseEntity<Siren> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(gameServices.getGameInfo(gameId).toGameInfoSiren())
    }

    companion object{
        private val logger = LoggerFactory.getLogger(LobbyController::class.java)
    }
}