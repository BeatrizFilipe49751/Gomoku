package daw.isel.pt.gomoku.repository.interfaces
import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User

interface UserRepository {
    fun getUser(userId: Int): User?
    fun createUser(username: String, email: String, token:String): User
    fun checkUserToken(token: String) : User?
    fun createLobby(userId: Int): Int
    fun quitLobby(lobbyId: Int): Boolean
    fun joinLobby(lobbyId: Int, userId: Int): Boolean
    fun getLobbies(): List<Lobby>
    fun isInLobby(userId: Int): Boolean
}