package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.controllers.utils.toGame
import daw.isel.pt.gomoku.controllers.utils.toGameSerialized
import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
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
                newGame.toGameSerialized(),
                playerBlack,
                playerWhite
            )

            if(wasCreated) newGame
            else throw IllegalStateException("Error creating Game") // to change
        }
    }

    fun getGame(gameId: String): Game {
        return transactionManager.run {
            when(val gameSerialized = it.gameRepository.getGame(gameId)) {
                null -> throw NotFoundException("Game not Found")
                else -> gameSerialized.toGame()
            }
        }
    }

    fun play(gameId: String, pieceToPlay: Piece) {
        transactionManager.run {
            val game = it.gameRepository.getGame(gameId)
        }
        /*
        check(state == GameState.ACTIVE) { "Game has finished" }
        check(currentTurn == pieceToPlace.color) { "Not your turn" }
        check(!board.hasPiece(pieceToPlace)) { "Piece already there" }
        val newBoard = board.copy(pieces = board.pieces + pieceToPlace)
        if (newBoard.pieces.size < WIN_STREAK)
            return copy(board = newBoard, currentTurn = currentTurn.switchTurn() )
        return if (checkWin(newBoard, pieceToPlace))
            copy(board = newBoard, state = GameState.FINISHED)
        else copy(board = newBoard, currentTurn = currentTurn.switchTurn() )
         */
    }
}