package daw.isel.pt.gomoku.services


import daw.isel.pt.gomoku.controllers.models.UserPoints
import daw.isel.pt.gomoku.domain.AuthUser
import daw.isel.pt.gomoku.domain.Token
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.domain.UserLogic
import daw.isel.pt.gomoku.repository.interfaces.transactions.TransactionManager
import daw.isel.pt.gomoku.services.exceptions.*
import org.springframework.stereotype.Component
import kotlinx.datetime.Clock

@Component
class UserServices(private val transactionManager: TransactionManager, private val clock: Clock,
                   private val usersDomain: UserLogic
) {
    fun getUser(id: Int): User {
        return transactionManager.run {
            it.usersRepository.getUser(id) ?:
                throw NotFoundException(UserErrorMessages.USER_NOT_FOUND)
        }
    }

    fun createUser(username: String?, email: String?, password:String?): User {
        if (username.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty())
            throw InvalidCredentialsException(UserErrorMessages.PARAMETERS_MISSING)

        if (!usersDomain.isSafePassword(password))
            throw InvalidCredentialsException(UserErrorMessages.INSECURE_PASSWORD)

        if(!email.contains("@"))
            throw InvalidCredentialsException(UserErrorMessages.EMAIL_WRONG_FORMAT)

        val passwordValidationInfo = usersDomain.createPasswordValidationInformation(password)

        return transactionManager.run {
            it.usersRepository.createUser(
                username = username,
                email = email,
                passwordValidation = passwordValidationInfo,
            )
        }
    }

    fun login(email: String?, password:String?) : AuthUser {
        if (email.isNullOrEmpty() || password.isNullOrEmpty())
            throw InvalidCredentialsException(UserErrorMessages.PARAMETERS_MISSING)

        return transactionManager.run {
            val repo = it.usersRepository
            val user = repo.getUserByEmail(email) ?:
                throw NotFoundException(UserErrorMessages.INVALID_CREDENTIALS)

            if (!usersDomain.validatePassword(password, user.passwordValidation)) {
                if (!usersDomain.validatePassword(password, user.passwordValidation)) {
                    throw InvalidCredentialsException(UserErrorMessages.INVALID_CREDENTIALS)
                }
            }

            val tokenValue = usersDomain.generateTokenValue()
            val now = clock.now()
            val newToken = Token(
                usersDomain.createTokenValidationInformation(tokenValue),
                user.userId,
                createdAt = now,
                lastUsedAt = now
            )
            repo.createToken(newToken, usersDomain.maxNumberOfTokensPerUser)
            AuthUser(
                token = tokenValue,
                user = user
            )
        }
    }

    fun logout(token: String): Boolean {
        val tokenValidationInfo = usersDomain.createTokenValidationInformation(token)
        return transactionManager.run {
            it.usersRepository.removeToken(tokenValidationInfo)
            true
        }
    }

    fun checkUserToken(token: String): AuthUser? {
        return transactionManager.run {
            val tokenValidationInfo = usersDomain.createTokenValidationInformation(token)
            val user = it.usersRepository.checkUserToken(
                tokenValidationInfo = tokenValidationInfo
            )
            if (user != null && usersDomain.isTokenTimeValid(clock, user.second)) {
                it.usersRepository.updateToken(user.second, clock.now())
                AuthUser(user.first, token)
            } else {
                null
            }
        }
    }

    fun getLeaderboard(): List<UserPoints> =
        transactionManager.run {
            it.usersRepository.getLeaderboard()
    }
}