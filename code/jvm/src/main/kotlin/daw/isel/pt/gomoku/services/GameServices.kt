package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.game.Piece
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import org.springframework.stereotype.Component

@Component
class GameServices(private val transactionManager: TransactionManager) {

    fun createGame(name: String) {

    }
    fun play(pieceToPlay: Piece) {

    }
}