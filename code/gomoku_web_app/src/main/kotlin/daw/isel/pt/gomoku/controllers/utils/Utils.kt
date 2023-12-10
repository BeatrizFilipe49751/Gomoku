package daw.isel.pt.gomoku.controllers.utils

import daw.isel.pt.gomoku.controllers.models.*
import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.domain.game.GameState.ACTIVE
import daw.isel.pt.gomoku.domain.game.GameState.FINISHED


const val MAX_LIMIT_SIZE = 5

fun Game.toGameSerialized(): GameSerialized {
    return GameSerialized(
        gameId = this.id,
        name = this.name,
        board = this.board.serialize(),
        state = this.state.stateChar,
        turn = this.currentTurn.color,
        opening = this.opening.id,
        variant = this.variant.id
    )
}

fun GameSerialized.toGame(): Game {
    return Game(
        id = this.gameId,
        board = Board.deserialize(this.board),
        name = name,
        state = this.state.toGameState(),
        currentTurn = this.turn.toPieceColor(),
        opening = this.opening.toOpening(),
        variant = this.variant.toVariant()
    )
}

fun Game?.toLobbyInfo(): LobbyInfo {
    return if (this == null)
        LobbyInfo(
            message = "Game not started",
            gameId = null
        )
    else LobbyInfo(
        message = "Game ${this.name} has started",
        gameId = this.id
    )
}



fun AllGameInfo.toGameOut(): GameOut{
    return GameOut(
        gameId = this.game.id,
        name = this.game.name,
        playerWhite = this.gameInfo.player_white,
        playerBlack = this.gameInfo.player_black,
        opening = this.game.opening.id,
        variant = this.game.variant.id,
        boardSize = this.game.board.size,
        currentTurn = this.game.currentTurn.color,
        pieces = this.game.board.serialize(),
        state = this.game.state.stateChar
    )
}

fun User.toUserOut(): UserOut{
    return UserOut(this.userId, this.username, this.email)
}

fun Lobby.toLobbyOut(): LobbyOut =
    LobbyOut(
        lobbyId = this.lobbyId,
        p1 =  this.p1,
        name = this.name
    )

fun String.putParameters(key: String, value: String) : String {
    return this.replace("{$key}", value)
}

fun validateSkipAndLimit(skip: Int, limit: Int, size: Int): Boolean =
    skip > 0 && limit <= MAX_LIMIT_SIZE && skip < size

const val emptySquare = "| "
const val blackPiece = "|B"
const val whitePiece = "|W"
val boardLine = "--".repeat(BOARD_DIM_MIN) + " \n"

fun Game.gameString(): String {
    val game = this
    val boardDim = game.board.size
    var boardString = ""
    boardString +=
        "GAME: ${game.name} IS ${if (game.state == ACTIVE) "ACTIVE" else "FINISHED"} \n "
    repeat(boardDim) { row ->
        boardString += boardLine
        repeat(boardDim) { col ->
            val piece = game.board.pieces.find {
                it.position == Position(
                    col.indexToColumn(boardDim),
                    row.indexToRow(boardDim),
                )
            }
            boardString += if (piece != null)
                if (piece.color == PieceColor.BLACK) blackPiece
                else whitePiece
            else emptySquare
        }
        boardString += "| \n "
    }
    boardString += boardLine
    if (game.state == FINISHED)
        boardString += "WINNER: ${game.currentTurn} \n"
    return boardString
}