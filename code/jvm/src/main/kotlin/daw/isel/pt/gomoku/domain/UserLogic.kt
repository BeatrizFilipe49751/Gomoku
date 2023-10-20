package daw.isel.pt.gomoku.domain

import org.springframework.stereotype.Component
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.springframework.security.crypto.password.PasswordEncoder
import java.security.SecureRandom
import java.util.*

@Component
class UserLogic (private val passwordEncoder: PasswordEncoder,
                 private val tokenEncoder: TokenEncoder,
                 private val config: UsersDomainConfig) {

    fun isSafePassword (password: String) : Boolean {
        return (password.length > 4) &&
                (password.contains(Regex("[A-Z]"))) &&
                (password.contains(Regex("[0-9]"))) &&
                (password.contains(Regex("[!@#$%&*()_+=|<>?{}\\[\\]~-]")))
    }

    fun createPasswordValidationInformation(password: String) = PasswordValidationInfo(
        validationInfo = passwordEncoder.encode(password)
    )

    fun validatePassword(password: String, passwordValidationInfo: PasswordValidationInfo) =
        passwordEncoder.matches(password, passwordValidationInfo.validationInfo)

    fun generateTokenValue(): String =
        ByteArray(config.tokenSizeInBytes).let { byteArray ->
            SecureRandom.getInstanceStrong().nextBytes(byteArray)
            Base64.getUrlEncoder().encodeToString(byteArray)
        }

    fun createTokenValidationInformation(token: String): TokenValidationInfo =
        tokenEncoder.createValidationInformation(token)

    val maxNumberOfTokensPerUser = config.maxTokensPerUser

    fun getTokenExpiration(token: Token): Instant {
        val absoluteExpiration = token.createdAt + config.tokenTtl
        val rollingExpiration = token.lastUsedAt + config.tokenRollingTtl
        return if (absoluteExpiration < rollingExpiration) {
            absoluteExpiration
        } else {
            rollingExpiration
        }
    }

    fun isTokenTimeValid(
        clock: Clock,
        token: Token
    ): Boolean {
        val now = clock.now()
        return token.createdAt <= now &&
                (now - token.createdAt) <= config.tokenTtl &&
                (now - token.lastUsedAt) <= config.tokenRollingTtl
    }
}