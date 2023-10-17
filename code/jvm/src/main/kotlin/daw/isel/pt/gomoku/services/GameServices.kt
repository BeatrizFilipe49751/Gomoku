package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.AlreadyInLobbyException
import daw.isel.pt.gomoku.services.exceptions.InvalidCredentialsException
import daw.isel.pt.gomoku.services.exceptions.LobbyErrorMessages
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameServices(private val transactionManager: TransactionManager) {

    fun createGame(name: String, playerBlack: Int, playerWhite: Int): Game {
        return transactionManager.run {
            it.gameRepository.createGame(
                Game(UUID.randomUUID(), Board(), name),
                playerBlack,
                playerWhite
            )
        }
    }

    fun getGame(gameId: String): Game {
        return transactionManager.run {
            it.gameRepository.getGame(gameId)
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