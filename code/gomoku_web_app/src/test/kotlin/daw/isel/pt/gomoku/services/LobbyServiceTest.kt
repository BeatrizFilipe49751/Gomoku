package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.services.exceptions.AlreadyInLobbyException
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import daw.isel.pt.gomoku.utils.TestUtils.lobbyServices
import daw.isel.pt.gomoku.utils.TestUtils.newLobbyName
import kotlin.test.*

class LobbyServiceTest {

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()

    @Test
    fun `Create a lobby Successfully`() {
        val user = createUserAndLogin()
        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name = newLobbyName()
        )
        assertEquals(lobby.p1, user.user.userId)

    }

    @Test
    fun `Create a lobby and get it by userId`() {
        val user = createUserAndLogin()
        lobbyServices.createLobby(userId = user.user.userId, newLobbyName())
        val lobby = lobbyServices.getLobbyByUserId(user.user.userId)
        println(lobby)
        assertNotNull(lobby)
    }

    @Test
    fun `Join a Lobby Successfully`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(user.user.userId, newLobbyName())
        assertTrue { lobbyServices.joinLobby(lobbyId = lobby.lobbyId, userId = otherUser.user.userId) }
    }

    @Test
    fun `Try to create a lobby when you are already in one`() {
        val user = createUserAndLogin()
        lobbyServices.createLobby(userId = user.user.userId, newLobbyName())
        assertFailsWith<AlreadyInLobbyException> { lobbyServices.createLobby(user.user.userId, newLobbyName()) }
    }


    @Test
    fun `Try to join a lobby when you are already in one`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(user.user.userId, newLobbyName())
        val otherUserLobby = lobbyServices.createLobby(otherUser.user.userId, newLobbyName())
        assertFailsWith<AlreadyInLobbyException> {
            lobbyServices.joinLobby(
                userId = otherUser.user.userId,
                lobbyId = lobby.lobbyId
            )
        }

        assertFailsWith<AlreadyInLobbyException> {
            lobbyServices.joinLobby(
                userId = user.user.userId,
                lobbyId = otherUserLobby.lobbyId
            )
        }

    }

    @Test
    fun `User joined a lobby but tries to create one while inside it`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()

        val lobby = lobbyServices.createLobby(user.user.userId, newLobbyName())
        lobbyServices.joinLobby(otherUser.user.userId, lobby.lobbyId)

        assertFailsWith<AlreadyInLobbyException> { lobbyServices.createLobby(otherUser.user.userId, newLobbyName()) }
    }

    @Test
    fun `User deletes lobby successfully`() {
        val user = createUserAndLogin()
        val lobby = lobbyServices.createLobby(user.user.userId, newLobbyName())
        assertTrue { lobbyServices.deleteLobby(user.user.userId, lobby.lobbyId) }
    }

    @Test
    fun `User quits lobby successfully`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(user.user.userId, newLobbyName())
        lobbyServices.joinLobby(otherUser.user.userId, lobby.lobbyId)
        assertTrue { lobbyServices.deleteLobby(otherUser.user.userId, lobby.lobbyId) }
    }

    @Test
    fun `lobby admin quits lobby and other user becomes admin`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(user.user.userId, newLobbyName())
        lobbyServices.joinLobby(otherUser.user.userId, lobby.lobbyId)
        val deleteLobby = lobbyServices.deleteLobby(user.user.userId, lobby.lobbyId)
        val changedLobby = lobbyServices.getLobby(lobby.lobbyId)
        assertTrue { deleteLobby }
        assertTrue { changedLobby.p1 == otherUser.user.userId }
    }

    @Test
    fun `p2 quits lobby successfully`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(user.user.userId, newLobbyName())
        lobbyServices.joinLobby(otherUser.user.userId, lobby.lobbyId)
        val deleteLobby = lobbyServices.deleteLobby(otherUser.user.userId, lobby.lobbyId)
        val changedLobby = lobbyServices.getLobby(lobby.lobbyId)
        assertTrue { deleteLobby }
        assertTrue { changedLobby.p2 == null }
    }

    @AfterTest
    fun resetAgain() = TestUtils.resetDatabase()
}