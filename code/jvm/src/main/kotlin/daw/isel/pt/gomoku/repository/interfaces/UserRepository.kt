package daw.isel.pt.gomoku.repository.interfaces
import daw.isel.pt.gomoku.controllers.models.UserPoints
import daw.isel.pt.gomoku.domain.*
import kotlinx.datetime.Instant

interface UserRepository {
    fun getUser(userId: Int): User?
    fun getUserByEmail(email: String) : User?
    fun getUsername(userId: Int): String
    fun createUser(username: String, email: String, passwordValidation: PasswordValidationInfo): User
    fun createToken(token : Token, maxTokens : Int)
    fun removeToken(token: TokenValidationInfo) : Int
    fun checkUserToken(tokenValidationInfo: TokenValidationInfo) : Pair<User, Token>?
    fun updateToken(token : Token, now : Instant)
    fun getLeaderboard(): List<UserPoints>
}