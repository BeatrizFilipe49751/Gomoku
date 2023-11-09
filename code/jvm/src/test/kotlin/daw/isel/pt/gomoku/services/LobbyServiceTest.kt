package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.game.Opening
import daw.isel.pt.gomoku.domain.game.Variant
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import daw.isel.pt.gomoku.utils.TestUtils.newLobbyName
import daw.isel.pt.gomoku.services.exceptions.AlreadyInLobbyException
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.lobbyServices
import kotlin.test.*

class LobbyServiceTest {

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()

    @Test
    fun `Create a lobby Successfully`() {
        val user = createUserAndLogin()
        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard
        )
        assertEquals(lobby.p1, user.user.userId)

    }

    @Test
    fun `Join a Lobby Successfully`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard
        )
        assertTrue { lobbyServices.joinLobby(lobbyId = lobby.lobbyId, userId = otherUser.user.userId) }
    }

    @Test
    fun `Try to create a lobby when you are already in one`() {
        val user = createUserAndLogin()
        lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard
        )
        assertFailsWith<AlreadyInLobbyException> { lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard) }
    }

    @Test
    fun `Try to join a lobby when you are already in one`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard
        )
        val otherUserLobby = lobbyServices.createLobby(
            userId = otherUser.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard
        )
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

        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard
        )
        lobbyServices.joinLobby(otherUser.user.userId, lobby.lobbyId)

        assertFailsWith<AlreadyInLobbyException> { lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard
        ) }
    }

    @Test
    fun `User deletes lobby successfully`() {
        val user = createUserAndLogin()
        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard)
        assertTrue { lobbyServices.deleteLobby(user.user.userId, lobby.lobbyId) }
    }

    @Test
    fun `User quits lobby successfully`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard)
        lobbyServices.joinLobby(otherUser.user.userId, lobby.lobbyId)
        assertTrue { lobbyServices.deleteLobby(otherUser.user.userId, lobby.lobbyId) }
    }

    @Test
    fun `lobby admin quits lobby and other user becomes admin`() {
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard)
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
        val lobby = lobbyServices.createLobby(
            userId = user.user.userId,
            name= newLobbyName(),
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            boardSize = TestUtils.smallBoard)
        lobbyServices.joinLobby(otherUser.user.userId, lobby.lobbyId)
        val deleteLobby = lobbyServices.deleteLobby(otherUser.user.userId, lobby.lobbyId)
        val changedLobby = lobbyServices.getLobby(lobby.lobbyId)
        assertTrue { deleteLobby }
        assertTrue { changedLobby.p2 == null }
    }

    @AfterTest
    fun resetAgain() = TestUtils.resetDatabase()
}