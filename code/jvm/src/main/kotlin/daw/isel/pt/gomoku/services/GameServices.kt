package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.game.Board
import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.game.Piece
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameServices(private val transactionManager: TransactionManager) {

    fun createGame(name: String) {
        //data.createGame(Game(UUID.randomUUID(), Board(), name))
    }
    fun play(pieceToPlay: Piece) {

    }
}