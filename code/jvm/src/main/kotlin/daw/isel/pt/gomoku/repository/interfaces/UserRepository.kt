package daw.isel.pt.gomoku.repository.interfaces
import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User

interface UserRepository {
    fun getUser(id: Int): User?
    fun createUser(username: String, email: String, token:String): User

    fun quitLobby(lobbyId: Int): Boolean
    fun createLobby(userId: Int): Int
    fun getLobbies(): List<Lobby>
    fun joinLobby(): Boolean
    fun checkUserToken(token: String) : User?
    fun canCreateLobby(userId: Int): Boolean
}