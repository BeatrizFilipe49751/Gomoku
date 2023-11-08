package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.controllers.models.AllGameInfo
import daw.isel.pt.gomoku.controllers.models.GameInfo
import daw.isel.pt.gomoku.controllers.models.PublicGameInfo
import daw.isel.pt.gomoku.controllers.utils.toGame
import daw.isel.pt.gomoku.controllers.utils.toGameSerialized
import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.domain.game.GameState.*
import daw.isel.pt.gomoku.domain.game.PieceColor.*
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.*
import org.springframework.stereotype.Component
import java.lang.IllegalStateException
import java.util.*

@Component
class GameServices(private val transactionManager: TransactionManager) {

    fun createGame(
        name: String, gameNumber: Int,
        opening: Int,
        variant: Int,
        playerBlack: Int,
        playerWhite: Int
    ): AllGameInfo {
        return transactionManager.run {
            val newGame = Game(
                id = UUID.randomUUID().toString(),
                board = Board(),
                name = name,
                opening = opening.toOpening(),
                variant = variant.toVariant()
            )

            val wasCreated = it.gameRepository.createGame(
                game = newGame.toGameSerialized(),
                gameNumber = gameNumber,
                playerBlack = playerBlack,
                playerWhite = playerWhite
            )

            val gameInfo = it.gameRepository.checkGameInfo(newGame.id)

            if(wasCreated && gameInfo != null){
                it.lobbyRepository.deleteLobby(gameNumber)
                AllGameInfo(newGame, gameInfo)
            }
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

    fun getGameInfo(gameId: String): PublicGameInfo {
        return transactionManager.run {
            val game = it.gameRepository.getGame(gameId)
            if(game != null) {
                val gameInfo = it.gameRepository.checkGameInfo(gameId)
                if(gameInfo!= null)
                    PublicGameInfo(
                        name= game.name,
                        playerBlack = gameInfo.player_black,
                        playerWhite = gameInfo.player_white,
                    )
                else throw NotFoundException(GameErrorMessages.GAME_NOT_FOUND)
            }  else throw UnauthorizedException(GameErrorMessages.INVALID_GAME_VIEWING)
        }
    }

    fun play(game: Game, userId: Int, row: Int, col: Int): AllGameInfo {
        return transactionManager.run {
            val boardDim = game.board.size
            val pieceToPlay =
                Piece(Position(row.indexToRow(boardDim), col.indexToColumn(boardDim)), game.currentTurn)
            val gameInfo = it.gameRepository.checkGameInfo(game.id)
                ?: throw NotFoundException("Game Not Found")
            userTurnCheck(game = game, userId = userId, gameInfo = gameInfo)
            try{
                val newGame = game.play(pieceToPlay)
                if (newGame.state == FINISHED) {
                    val points = winPoints + bonusPoints
                    val username = it.usersRepository.getUsername(userId)
                    if (it.gameRepository.getLeaderboardUsername(username) != null)
                        it.gameRepository.addUserPoints(username, points)
                    else it.gameRepository.addUserToLeaderboard(username, points)
                }
                if (it.gameRepository.updateGame(newGame.toGameSerialized())){
                    AllGameInfo(newGame, gameInfo)
                }
                else throw IllegalStateException("Game failed to update")
            } catch(ex: IllegalStateException) {
                throw GameError(ex.message ?: "No message provided")
            }
        }
    }

    private fun userTurnCheck(game: Game, userId: Int, gameInfo: GameInfo) {
        when (game.currentTurn) {
            BLACK -> {
                if (gameInfo.player_black != userId) {
                    throw GameError(GameErrorMessages.NOT_YOUR_TURN)
                }
            }
            WHITE -> {
                if (gameInfo.player_white != userId) {
                    throw GameError(GameErrorMessages.NOT_YOUR_TURN)
                }
            }
        }
    }
}