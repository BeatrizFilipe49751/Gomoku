package daw.isel.pt.gomoku.repository.dataJDBI

import daw.isel.pt.gomoku.repository.interfaces.Transaction
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.jdbi.v3.core.Handle


class JdbiTransaction(
    private val handle: Handle
) : Transaction {

    override val usersRepository: UserRepository = UsersDataJDBI(handle)

    override fun rollback() {
        handle.rollback()
    }
}