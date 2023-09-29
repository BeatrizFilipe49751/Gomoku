package daw.isel.pt.gomoku.repository.dataMem

import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import org.springframework.stereotype.Component

@Component
class UsersDataMem: UserRepository {
    override fun getUser(id: Int): User {
        TODO("Not yet implemented")
    }

    override fun createUser(username: String): Int {
        TODO("Not yet implemented")
    }
}