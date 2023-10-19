package daw.isel.pt.gomoku.utils

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.dataJDBI.transactions.JdbiTransactionManager
import daw.isel.pt.gomoku.services.LobbyServices
import daw.isel.pt.gomoku.services.UserServices
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*
import kotlin.math.abs
import kotlin.random.Random

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


    fun resetDatabase() = runWithHandle {
        it.createUpdate("TRUNCATE game_users, games, lobby, users;")
            .execute()
    }
    fun runWithHandle(block: (Handle) -> Unit) = jdbi.useTransaction<Exception>(block)

    val lobbyServices = LobbyServices(JdbiTransactionManager(jdbi))
    val userServices = UserServices(JdbiTransactionManager(jdbi))

    fun createUser(): User {
        return userServices.createUser(
            newTestUserName(),
            newTestEmail()
        )
    }
    fun joinLobby(user: User, lobby: Lobby): Boolean {
        return lobbyServices
            .joinLobby(
                userId = user.userId,
                lobbyId = lobby.lobbyId
            )
    }

    fun createLobby(user: User): Lobby {
        return lobbyServices
            .createLobby(
                userId = user.userId,
                name = newLobbyName()
            )
    }


    fun newGameName() = "game-${abs(Random.nextLong())}@gmail.com"
    fun newTestUserName() = "user-${abs(Random.nextLong())}"

    fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"

    fun newToken() = UUID.randomUUID().toString()
    fun newLobbyName() = "lobby-${abs(Random.nextLong())}"

}