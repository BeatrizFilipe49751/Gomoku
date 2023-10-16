package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.dataJDBI.transactions.JdbiTransactionManager
import daw.isel.pt.gomoku.services.exceptions.AlreadyInLobbyException
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.postgresql.ds.PGSimpleDataSource
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class LobbyServiceTest {

    @Test
    fun `Create a lobby Successfully`() {
        val user = createUser()
        val lobby = lobbyServices.createLobby(user.userId)
        assertEquals(lobby.p1, user.userId)

    }

    @Test
    fun `Join a Lobby Successfully`() {
        val user = createUser()
        val otherUser = createUser()
        val lobby = lobbyServices.createLobby(user.userId)
        assertTrue { lobbyServices.joinLobby(lobbyId = lobby.lobbyId, userId = otherUser.userId) }
    }
    @Test
    fun `Try to create a lobby when you are already in one`() {
        val user = createUser()
        lobbyServices.createLobby(userId = user.userId)
        assertFailsWith<AlreadyInLobbyException> { lobbyServices.createLobby(user.userId) }
    }

    @Test
    fun `Try to join a lobby when you are already in one`() {
        val user = createUser()
        val otherUser  = createUser()
        val lobby = lobbyServices.createLobby(user.userId)
        val otherUserLobby = lobbyServices.createLobby(otherUser.userId)
        assertFailsWith<AlreadyInLobbyException> { lobbyServices.joinLobby(
            userId = otherUser.userId,
            lobbyId = lobby.lobbyId
        ) }

        assertFailsWith<AlreadyInLobbyException> {
            lobbyServices.joinLobby(
                userId = user.userId,
                lobbyId = otherUserLobby.lobbyId
            )
        }

    }

    @Test
    fun `User joined a lobby but tries to create one while inside it`() {
        val user = createUser()
        val otheruser = createUser()

        val lobby = lobbyServices.createLobby(user.userId)
        lobbyServices.joinLobby(otheruser.userId, lobby.lobbyId)

        assertFailsWith<AlreadyInLobbyException> { lobbyServices.createLobby(otheruser.userId) }
    }

    @Test
    fun `User deletes lobby successfully`(){

    }
    companion object {

        private fun createUser(): User {
            return userServices.createUser(
                newTestUserName(),
                newTestEmail()
            )
        }
        private fun newTestUserName() = "user-${abs(Random.nextLong())}"

        private fun newTestEmail() = "email-${abs(Random.nextLong())}@gmail.com"


        private val jdbi = Jdbi.create(
            PGSimpleDataSource().apply {
                setURL(System.getenv("JDBC_DATABASE_URL"))
            }
        )
            .installPlugin(KotlinPlugin())
            .installPlugin(PostgresPlugin())

        private val lobbyServices = LobbyServices(JdbiTransactionManager(jdbi))
        private val userServices = UserServices(JdbiTransactionManager(jdbi))
    }
}