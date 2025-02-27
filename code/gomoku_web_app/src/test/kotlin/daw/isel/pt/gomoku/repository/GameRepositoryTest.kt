package daw.isel.pt.gomoku.repository

import daw.isel.pt.gomoku.controllers.utils.toGameSerialized
import daw.isel.pt.gomoku.domain.game.Board
import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.game.Piece
import daw.isel.pt.gomoku.domain.game.PieceColor.BLACK
import daw.isel.pt.gomoku.domain.game.Position
import daw.isel.pt.gomoku.repository.dataJDBI.GameDataJDBI
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import daw.isel.pt.gomoku.utils.TestUtils.newGameName
import daw.isel.pt.gomoku.utils.TestUtils.runWithHandle
import daw.isel.pt.gomoku.utils.TestUtils.smallBoard
import java.util.*
import kotlin.random.Random
import kotlin.test.*

class GameRepositoryTest {

    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()
    @Test
    fun `Create Game Successfully`() = runWithHandle{
        val repo = GameDataJDBI(it)
        val game = Game(
                id = UUID.randomUUID().toString(),
                board = Board(),
                name = newGameName(),
            )
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        assertTrue {  repo.createGame(game.toGameSerialized(), Random.nextLong().toInt(), user.user.userId, otherUser.user.userId)}
    }

    @Test
    fun `Get Game successfully`() = runWithHandle{
        val repo = GameDataJDBI(it)
        val game = Game(
            id = UUID.randomUUID().toString(),
            board = Board(),
            name = newGameName(),
        )
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        val serializedGame = game.toGameSerialized()
        repo.createGame(serializedGame, Random.nextLong().toInt(), user.user.userId, otherUser.user.userId)

        assertEquals(serializedGame, repo.getGame(game.id))
    }

    @Test
    fun `Update Game Successfully`() = runWithHandle{
        val repo = GameDataJDBI(it)
        val game = Game(
            id = UUID.randomUUID().toString(),
            board = Board(),
            name = newGameName(),
        )
        val user = createUserAndLogin()
        val otherUser = createUserAndLogin()
        repo.createGame(game.toGameSerialized(), Random.nextLong().toInt(), user.user.userId, otherUser.user.userId)
        val newGame = game.play(Piece(position = Position(0, 0, smallBoard), BLACK))
        assertTrue { repo.updateGame(newGame.toGameSerialized()) }
    }

    @AfterTest
    fun resetAgain() = TestUtils.resetDatabase()

}