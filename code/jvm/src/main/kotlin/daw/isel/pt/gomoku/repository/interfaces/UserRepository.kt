package daw.isel.pt.gomoku.repository.interfaces
import daw.isel.pt.gomoku.domain.User

interface UserRepository {
    fun getUser(id: Int): User?
    fun createUser(username: String, email: String, token:String): User
}