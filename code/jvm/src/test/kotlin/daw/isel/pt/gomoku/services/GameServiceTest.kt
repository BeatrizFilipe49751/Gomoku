package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.services.exceptions.GameError
import daw.isel.pt.gomoku.services.exceptions.GameErrorMessages
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.UserInfo
import daw.isel.pt.gomoku.utils.TestUtils.createLobby
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import daw.isel.pt.gomoku.utils.TestUtils.gameServices
import daw.isel.pt.gomoku.utils.TestUtils.newGameName
import kotlin.test.*

class GameServiceTest {
    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()
    @Test
    fun `Create a game successfully`() {
        val user1 = createUserAndLogin()
        val user2 = createUserAndLogin()
        val lobby = createLobby(user1)
        val gameName = newGameName()
        val game = gameServices.createGame(gameName, lobby.lobbyId, user1.user.userId, user2.user.userId)
        assertEquals(gameName, game.name)
        assertTrue(game.board.isEmptyBoard)
        assertEquals('A', game.state.stateChar)
        assertEquals('b', game.currentTurn.color)
    }

    @Test
    fun `Get Game successfully`() {
        val created = createNewGame().game
        val game = gameServices.getGame(created.id)
        assertEquals(created.id, game.id)
        assertEquals(created.board, game.board)
        assertEquals(created.name, game.name)
        assertEquals(created.state, game.state)
        assertEquals(created.currentTurn, game.currentTurn)
    }

    @Test
    fun `Execute two plays successfully`() {
        val gameInfo = createNewGame()
        val game = gameInfo.game
        val user1 = gameInfo.user1
        val user2 = gameInfo.user2
        val playedGameB = gameServices.play(game, user1.user.userId, 0, 0)
        assertGameInfoAfterPlay(game, playedGameB)
        assertFalse(playedGameB.board.isEmptyBoard)
        assertTrue(playedGameB.board.pieces.size == 1)
        assertTrue(playedGameB.board.pieces.contains(
            Piece(
                Position(
                    0.indexToRow(),
                    0.indexToColumn()
                ),
                PieceColor.BLACK
            )
        ))
        val playedGameW = gameServices.play(playedGameB, user2.user.userId, 12, 12)
        assertGameInfoAfterPlay(playedGameB, playedGameW)
        assertTrue(playedGameW.board.pieces.size == 2)
        assertTrue(playedGameW.board.pieces.containsAll(
            listOf(
                Piece(
                    Position(
                        0.indexToRow(),
                        0.indexToColumn()
                    ),
                    PieceColor.BLACK
                ),
                Piece(
                    Position(
                        12.indexToRow(),
                        12.indexToColumn()
                    ),
                    PieceColor.WHITE
                )
            )
        ))
    }

    @Test
    fun `Execute wrong turn play`() {
        val gameInfo = createNewGame()
        val game = gameInfo.game
        val user1 = gameInfo.user1
        val playedGameB = gameServices.play(game, user1.user.userId, 0, 0)
        val ex = assertFailsWith<GameError> { gameServices.play(playedGameB, user1.user.userId, 12, 12)  }
        assertEquals(GameErrorMessages.NOT_YOUR_TURN, ex.message)
    }

    @Test
    fun `Play out of bounds`() {
        val gameInfo = createNewGame()
        val game = gameInfo.game
        val user1 = gameInfo.user1
        val ex = assertFailsWith<GameError> { gameServices.play(game, user1.user.userId, 15, 15)  }
        assertEquals(GameErrorMessages.INDEX_OUT_OF_BOUNDS, ex.message)
    }

    @Test
    fun `Play in occupied space`() {
        val gameInfo = createNewGame()
        val game = gameInfo.game
        val user1 = gameInfo.user1
        val user2 = gameInfo.user2
        val playedGameB = gameServices.play(game, user1.user.userId, 0, 0)
        val ex = assertFailsWith<GameError> { gameServices.play(playedGameB, user2.user.userId, 0, 0)  }
        assertEquals(GameErrorMessages.INVALID_PLAY, ex.message)
    }

    @Test
    fun `Get a game that does not exist`() {
        assertFailsWith<NotFoundException> { gameServices.getGame("fake-id") }
    }

    companion object {
        private fun assertGameInfoAfterPlay(beforeGame: Game, afterGame: Game) {
            assertEquals(beforeGame.id, afterGame.id)
            assertEquals(beforeGame.name, afterGame.name)
            assertEquals(beforeGame.state, afterGame.state)
            assertNotEquals(beforeGame.currentTurn, afterGame.currentTurn)
        }

        data class GameInfo(val user1: UserInfo, val user2: UserInfo, val lobby: Lobby, val game: Game)

        fun createNewGame(): GameInfo {
            val user1 = createUserAndLogin()
            val user2 = createUserAndLogin()
            val lobby = createLobby(user1)
            val game = gameServices.createGame(newGameName(), lobby.lobbyId, user1.user.userId, user2.user.userId)
            return GameInfo(user1, user2, lobby, game)
        }
    }

    @BeforeTest
    fun resetAgain() = TestUtils.resetDatabase()

}