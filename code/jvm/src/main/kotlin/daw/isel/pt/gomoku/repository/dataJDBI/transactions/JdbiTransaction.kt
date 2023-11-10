package daw.isel.pt.gomoku.repository.dataJDBI.transactions

import daw.isel.pt.gomoku.repository.dataJDBI.GameDataJDBI
import daw.isel.pt.gomoku.repository.dataJDBI.LobbyDataJDBI
import daw.isel.pt.gomoku.repository.dataJDBI.UsersDataJDBI
import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import daw.isel.pt.gomoku.repository.interfaces.LobbyRepository
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import daw.isel.pt.gomoku.repository.interfaces.transactions.Transaction
import org.jdbi.v3.core.Handle


class JdbiTransaction(
    private val handle: Handle
) : Transaction {

    override val usersRepository: UserRepository = UsersDataJDBI(handle)
    override val lobbyRepository: LobbyRepository = LobbyDataJDBI(handle)
    override val gameRepository: GameRepository = GameDataJDBI(handle)
    override fun rollback() {
        handle.rollback()
    }
}