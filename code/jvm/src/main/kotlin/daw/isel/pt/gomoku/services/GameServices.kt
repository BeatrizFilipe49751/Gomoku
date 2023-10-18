package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.controllers.models.TurnInfo
import daw.isel.pt.gomoku.controllers.utils.toGame
import daw.isel.pt.gomoku.controllers.utils.toGameSerialized
import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.domain.game.PieceColor.*
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.*
import org.springframework.stereotype.Component
import java.lang.IllegalStateException
import java.util.*

@Component
class GameServices(private val transactionManager: TransactionManager) {

    fun createGame(name: String, playerBlack: Int, playerWhite: Int): Game {
        return transactionManager.run {
            val newGame = Game(
                id = UUID.randomUUID().toString(),
                board = Board(),
                name = name
            )
            val wasCreated = it.gameRepository.createGame(
                game = newGame.toGameSerialized(),
                playerBlack = playerBlack,
                playerWhite = playerWhite
            )

            if(wasCreated) newGame
            else throw GameError(GameErrorMessages.GAME_CREATION_ERROR)
        }
    }

    fun getGame(gameId: String): Game {
        return transactionManager.run {
            when(val gameSerialized = it.gameRepository.getGame(gameId)) {
                null -> throw NotFoundException(GameErrorMessages.GAME_NOT_FOUND)
                else -> gameSerialized.toGame()
            }
        }
    }

    fun play(game: Game, userId: Int, row: Int, col: Int): Game {
        return transactionManager.run {
            val pieceToPlay =
                Piece(Position(row.indexToRow(), col.indexToColumn()), game.currentTurn)
            val turn = it.gameRepository.checkTurn(game.id) ?: throw NotFoundException("Game Not Found")
            playChecks(
                game = game,
                pieceToPlay = pieceToPlay,
                userId = userId,
                turn = turn
            )
            val newGame = game.play(pieceToPlay)
            if (it.gameRepository.updateGame(newGame.toGameSerialized()))
                newGame
            else throw IllegalStateException("Game failed to update")
        }
    }

    private fun playChecks(game: Game, pieceToPlay: Piece, userId: Int, turn: TurnInfo) {
        if (game.state == GameState.FINISHED)
            throw GameError(GameErrorMessages.GAME_FINISHED)
        if (game.currentTurn != pieceToPlay.color)
            throw GameError(GameErrorMessages.NOT_YOUR_TURN)
        if (game.board.hasPiece(pieceToPlay))
            throw GameError(GameErrorMessages.INVALID_PLAY)

        when (game.currentTurn) {
            BLACK -> {
                if (turn.player_black != userId) {
                    throw GameError(GameErrorMessages.NOT_YOUR_TURN)
                }
            }
            WHITE -> {
                if (turn.player_white != userId) {
                    throw GameError(GameErrorMessages.NOT_YOUR_TURN)
                }
            }
        }
    }
}