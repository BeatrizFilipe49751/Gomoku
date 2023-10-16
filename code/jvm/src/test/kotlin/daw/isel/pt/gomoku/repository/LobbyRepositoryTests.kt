package daw.isel.pt.gomoku.repository

import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.dataJDBI.LobbyDataJDBI
import daw.isel.pt.gomoku.repository.dataJDBI.UsersDataJDBI
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.postgresql.ds.PGSimpleDataSource
import java.util.*
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class LobbyRepositoryTests {

    @Test
    fun `create Lobby Successfully`() = runWithHandle{
        val usersRepo = UsersDataJDBI(it)
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUser(usersRepo)
        val newLobby = lobbyRepo.createLobby(user.userId)
        assertTrue {  newLobby.p1 == user.userId && newLobby.p2 == null }
    }

    @Test
    fun `join Lobby Successfully`() = runWithHandle {
        val usersRepo = UsersDataJDBI(it)
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUser(usersRepo)
        val newLobby = lobbyRepo.createLobby(user.userId)

        val otherUser = createUser(usersRepo)
        assertTrue { lobbyRepo.joinLobby(newLobby.lobbyId, otherUser.userId) }
        assertTrue { !lobbyRepo.isNotInLobby(user.userId) }
        assertTrue { !lobbyRepo.isNotInLobby(otherUser.userId) }
    }

    @Test
    fun `delete Lobby Successfully`() = runWithHandle {
        val usersRepo = UsersDataJDBI(it)
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUser(usersRepo)
        val newLobby = lobbyRepo.createLobby(user.userId)

        assertTrue {lobbyRepo.deleteLobby(newLobby.lobbyId)}
    }

    @Test
    fun `quit Lobby Successfully`() = runWithHandle {
        val usersRepo = UsersDataJDBI(it)
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUser(usersRepo)
        val otherUser = createUser(usersRepo)
        val newLobby = lobbyRepo.createLobby(user.userId)
        lobbyRepo.joinLobby(newLobby.lobbyId, otherUser.userId)
        assertTrue {lobbyRepo.quitLobby(newLobby.lobbyId, otherUser.userId)}
    }

    companion object {

        private fun createUser(userRepo : UsersDataJDBI): User {
            return userRepo.createUser(
                username = newTestUserName(),
                email = newTestEmail(),
                token = newToken()
            )
        }
        private fun runWithHandle(block: (Handle) -> Unit) = LobbyRepositoryTests.jdbi.useTransaction<Exception>(block)
        private fun newTestUserName() = "user-${abs(Random.nextLong())}"

        private fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"

        private fun newToken() = UUID.randomUUID().toString()

        private val jdbi = Jdbi.create(
            PGSimpleDataSource().apply {
                setURL(System.getenv("JDBC_DATABASE_URL"))
            }
        )
            .installPlugin(KotlinPlugin())
            .installPlugin(PostgresPlugin())
    }
}