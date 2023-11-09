package daw.isel.pt.gomoku.utils

import daw.isel.pt.gomoku.domain.*
import daw.isel.pt.gomoku.domain.game.Opening
import daw.isel.pt.gomoku.domain.game.Variant
import daw.isel.pt.gomoku.repository.dataJDBI.mappers.PasswordValidationInfoMapper
import daw.isel.pt.gomoku.repository.dataJDBI.transactions.JdbiTransactionManager
import daw.isel.pt.gomoku.services.GameServices
import daw.isel.pt.gomoku.services.LobbyServices
import daw.isel.pt.gomoku.services.UserServices
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.math.abs
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

object TestUtils {
    fun createNewClient(port: Int): WebTestClient {
        return WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
    }

    private val jdbi = Jdbi.create(
        PGSimpleDataSource().apply {
            setURL(System.getenv("JDBC_DATABASE_URL"))
        }
    )
        .installPlugin(KotlinPlugin())
        .installPlugin(PostgresPlugin())
        .registerColumnMapper(PasswordValidationInfoMapper())


    fun resetDatabase() = runWithHandle {
        it.createUpdate("TRUNCATE game_users, games, lobby, tokens, users;")
            .execute()
    }
    fun runWithHandle(block: (Handle) -> Unit) = jdbi.useTransaction<Exception>(block)

    val gameServices = GameServices(JdbiTransactionManager(jdbi))
    val lobbyServices = LobbyServices(JdbiTransactionManager(jdbi))
    val userServices = createUsersService(TestClock())

    private fun createUsersService(
        testClock: TestClock,
        tokenTtl: Duration = 30.days,
        tokenRollingTtl: Duration = 30.minutes,
        maxTokensPerUser: Int = 3
    ) = UserServices(
        JdbiTransactionManager(jdbi),
        testClock,
        UserLogic(
            BCryptPasswordEncoder(),
            Sha256TokenEncoder(),
            UsersDomainConfig(
                tokenSizeInBytes = 256 / 8,
                tokenTtl = tokenTtl,
                tokenRollingTtl,
                maxTokensPerUser = maxTokensPerUser
            )
    ),

    )
    fun createUserAndLogin(): AuthUser {
        val password = newTestPassword()
        val user = userServices.createUser(
            newTestUserName(),
            newTestEmail(),
            password
        )
        return userServices.login(user.email, password)
    }
    fun joinLobby(userInfo: AuthUser, lobby: Lobby): Boolean {
        return lobbyServices
            .joinLobby(
                userId = userInfo.user.userId,
                lobbyId = lobby.lobbyId
            )
    }

    fun  createLobby(userInfo: AuthUser): Lobby {
        return lobbyServices
            .createLobby(
                userId = userInfo.user.userId,
                name = newLobbyName(),
                Opening.PRO.id,
                Variant.FREESTYLE.id,
                smallBoard
            )
    }

    fun newGameName() = "game-${abs(Random.nextLong())}"
    fun newTestUserName() = "user-${abs(Random.nextLong())}"
    fun newTestPassword() = "password-A#${abs(Random.nextLong())}"
    fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"
    fun newLobbyName() = "lobby-${abs(Random.nextLong())}"

    val bigBoard = 19
    val smallBoard = 15

}