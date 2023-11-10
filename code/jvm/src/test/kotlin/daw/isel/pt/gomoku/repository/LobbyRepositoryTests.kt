package daw.isel.pt.gomoku.repository

import daw.isel.pt.gomoku.repository.dataJDBI.LobbyDataJDBI
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import daw.isel.pt.gomoku.utils.TestUtils.newLobbyName
import daw.isel.pt.gomoku.utils.TestUtils.runWithHandle
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class LobbyRepositoryTests {

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()
    @Test
    fun `create Lobby Successfully`() = runWithHandle{
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUserAndLogin()
        val newLobby = lobbyRepo.createLobby(user.user.userId, newLobbyName())
        assertTrue {  newLobby.p1 == user.user.userId && newLobby.p2 == null }
    }

    @Test
    fun `join Lobby Successfully`() = runWithHandle {
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUserAndLogin()
        val newLobby = lobbyRepo.createLobby(user.user.userId, newLobbyName())

        val otherUser = createUserAndLogin()
        assertTrue { lobbyRepo.joinLobby(newLobby.lobbyId, otherUser.user.userId) }
        assertTrue { !lobbyRepo.isNotInLobby(user.user.userId) }
        assertTrue { !lobbyRepo.isNotInLobby(otherUser.user.userId) }
    }

    @Test
    fun `delete Lobby Successfully`() = runWithHandle {
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUserAndLogin()
        val newLobby = lobbyRepo.createLobby(user.user.userId, newLobbyName())

        assertTrue {lobbyRepo.deleteLobby(newLobby.lobbyId)}
    }

    @Test
    fun `quit Lobby Successfully`() = runWithHandle {
        val lobbyRepo = LobbyDataJDBI(it)
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val newLobby = lobbyRepo.createLobby(user.user.userId, newLobbyName())
        lobbyRepo.joinLobby(newLobby.lobbyId, otherUser.user.userId)
        assertTrue {lobbyRepo.quitLobby(newLobby.lobbyId, otherUser.user.userId)}
    }

    @AfterTest
    fun resetAgain() = TestUtils.resetDatabase()
}