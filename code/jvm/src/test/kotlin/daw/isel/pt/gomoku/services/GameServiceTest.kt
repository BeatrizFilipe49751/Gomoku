package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.controllers.models.AllGameInfo
import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.services.exceptions.GameError
import daw.isel.pt.gomoku.services.exceptions.GameErrorMessages
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import daw.isel.pt.gomoku.utils.TestUtils
import daw.isel.pt.gomoku.utils.TestUtils.createLobby
import daw.isel.pt.gomoku.utils.TestUtils.createUserAndLogin
import daw.isel.pt.gomoku.utils.TestUtils.gameServices
import daw.isel.pt.gomoku.utils.TestUtils.newGameName
import daw.isel.pt.gomoku.utils.TestUtils.smallBoard
import kotlin.test.*

class GameServiceTest {
    @BeforeTest
    fun resetInit() = TestUtils.resetDatabase()
    @Test
    fun `Create a game successfully`() {
        val authedUser1 = createUserAndLogin()
        val authedUser2 = createUserAndLogin()
        val lobby = createLobby(authedUser1)
        val gameName = newGameName()
        val allgameinfo = gameServices.createGame(
            name=gameName,
            gameNumber= lobby.lobbyId,
            opening = Opening.FREESTYLE.id,
            variant = Variant.FREESTYLE.id,
            playerBlack = authedUser1.user.userId,
            playerWhite = authedUser2.user.userId
        )
        assertEquals(gameName, allgameinfo.game.name)
        assertTrue(allgameinfo.game.board.isEmptyBoard)
        assertEquals('A', allgameinfo.game.state.stateChar)
        assertEquals('b', allgameinfo.game.currentTurn.color)
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
        val allGameInfo = createNewGame()
        val game = allGameInfo.game
        val user1 = allGameInfo.gameInfo.player_black
        val user2 = allGameInfo.gameInfo.player_white
        val playedGameB = gameServices.play(game, user1, 0, 0)
        assertGameInfoAfterPlay(game, playedGameB.game)
        assertFalse(playedGameB.game.board.isEmptyBoard)
        assertTrue(playedGameB.game.board.pieces.size == 1)
        assertTrue(playedGameB.game.board.pieces.contains(
            Piece(
                Position(
                    0.indexToRow(smallBoard),
                    0.indexToColumn(smallBoard)
                ),
                PieceColor.BLACK
            )
        ))
        val playedGameW = gameServices.play(playedGameB.game, user2, 12, 12)
        assertGameInfoAfterPlay(playedGameB.game, playedGameW.game)
        assertTrue(playedGameW.game.board.pieces.size == 2)
        assertTrue(playedGameW.game.board.pieces.containsAll(
            listOf(
                Piece(
                    Position(
                        0.indexToRow(smallBoard),
                        0.indexToColumn(smallBoard)
                    ),
                    PieceColor.BLACK
                ),
                Piece(
                    Position(
                        12.indexToRow(smallBoard),
                        12.indexToColumn(smallBoard)
                    ),
                    PieceColor.WHITE
                )
            )
        ))
    }

    @Test
    fun `Execute wrong turn play`() {
        val allGameInfo = createNewGame()
        val game = allGameInfo.game
        val user1 = allGameInfo.gameInfo.player_black
        val playedGameB = gameServices.play(game, user1, 0, 0)
        val ex = assertFailsWith<GameError> { gameServices.play(playedGameB.game, user1, 12, 12)  }
        assertEquals(GameErrorMessages.NOT_YOUR_TURN, ex.message)
    }

    @Test
    fun `Play out of bounds`() {
        val allGameInfo = createNewGame()
        val game = allGameInfo.game
        val user1 = allGameInfo.gameInfo.player_black
        val ex = assertFailsWith<GameError> { gameServices.play(game, user1, 15, 15)  }
        assertEquals(GameErrorMessages.indexOutOfBoundsMessage(smallBoard - 1), ex.message)
    }

    @Test
    fun `Play in occupied space`() {
        val allGameInfo = createNewGame()
        val game = allGameInfo.game
        val user1 = allGameInfo.gameInfo.player_black
        val user2 = allGameInfo.gameInfo.player_white
        val playedGameB = gameServices.play(game, user1, 0, 0)
        val ex = assertFailsWith<GameError> { gameServices.play(playedGameB.game, user2, 0, 0)  }
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

        fun createNewGame(): AllGameInfo {
            val user1 = createUserAndLogin()
            val user2 = createUserAndLogin()
            val lobby = createLobby(user1)
            return gameServices.createGame(
                name= newGameName(),
                gameNumber = lobby.lobbyId,
                opening = Opening.FREESTYLE.id,
                variant = Variant.FREESTYLE.id,
                playerBlack= user1.user.userId,
                playerWhite= user2.user.userId,

            )
        }
    }

    @BeforeTest
    fun resetAgain() = TestUtils.resetDatabase()

}