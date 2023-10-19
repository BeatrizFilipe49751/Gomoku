package daw.isel.pt.gomoku.repository

import daw.isel.pt.gomoku.utils.TestUtils.createUser
import daw.isel.pt.gomoku.utils.TestUtils.newLobbyName
import daw.isel.pt.gomoku.utils.TestUtils.runWithHandle
import daw.isel.pt.gomoku.repository.dataJDBI.LobbyDataJDBI
import daw.isel.pt.gomoku.utils.TestUtils
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

        val user = createUser()
        val newLobby = lobbyRepo.createLobby(user.userId, newLobbyName())
        assertTrue {  newLobby.p1 == user.userId && newLobby.p2 == null }
    }

    @Test
    fun `join Lobby Successfully`() = runWithHandle {
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUser()
        val newLobby = lobbyRepo.createLobby(user.userId, newLobbyName())

        val otherUser = createUser()
        assertTrue { lobbyRepo.joinLobby(newLobby.lobbyId, otherUser.userId) }
        assertTrue { !lobbyRepo.isNotInLobby(user.userId) }
        assertTrue { !lobbyRepo.isNotInLobby(otherUser.userId) }
    }

    @Test
    fun `delete Lobby Successfully`() = runWithHandle {
        val lobbyRepo = LobbyDataJDBI(it)

        val user = createUser()
        val newLobby = lobbyRepo.createLobby(user.userId, newLobbyName())

        assertTrue {lobbyRepo.deleteLobby(newLobby.lobbyId)}
    }

    @Test
    fun `quit Lobby Successfully`() = runWithHandle {
        val lobbyRepo = LobbyDataJDBI(it)
        val user = createUser()
        val otherUser = createUser()
        val newLobby = lobbyRepo.createLobby(user.userId, newLobbyName())
        lobbyRepo.joinLobby(newLobby.lobbyId, otherUser.userId)
        assertTrue {lobbyRepo.quitLobby(newLobby.lobbyId, otherUser.userId)}
    }

    @AfterTest
    fun resetAgain() = TestUtils.resetDatabase()
}