package daw.isel.pt.gomoku.controllers

import daw.isel.pt.gomoku.controllers.models.GameOut
import daw.isel.pt.gomoku.controllers.models.PlayIn
import daw.isel.pt.gomoku.controllers.routes.GameRoutes
import daw.isel.pt.gomoku.controllers.utils.gameString
import daw.isel.pt.gomoku.controllers.utils.getTokenFromRequest
import daw.isel.pt.gomoku.controllers.utils.toGameOut
import daw.isel.pt.gomoku.services.GameServices
import daw.isel.pt.gomoku.services.UserServices
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(val gameServices: GameServices, val userServices: UserServices) {
    @PutMapping(GameRoutes.PLAY)
    fun play(
        request : HttpServletRequest,
        @RequestBody playIn: PlayIn
    ): ResponseEntity<GameOut> {
        val user = userServices.getUserByToken(
            request.getTokenFromRequest()
        )

        val game = gameServices.getGame(
            gameId = playIn.gameId
        )
        val newGame = gameServices.play(
            game = game,
            userId = user.userId,
            row = playIn.row,
            col = playIn.col
        )
        logger.info("\n" + newGame.gameString())
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(newGame.toGameOut())
    }

    companion object{
        private val logger = LoggerFactory.getLogger(LobbyController::class.java)
    }
}