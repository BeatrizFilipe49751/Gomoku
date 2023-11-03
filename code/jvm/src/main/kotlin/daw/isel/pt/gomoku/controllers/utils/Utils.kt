package daw.isel.pt.gomoku.controllers.utils

import daw.isel.pt.gomoku.controllers.models.*
import daw.isel.pt.gomoku.controllers.pipeline.AuthInterceptor
import daw.isel.pt.gomoku.domain.Lobby
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.domain.game.GameState.*
import jakarta.servlet.http.HttpServletRequest

fun HttpServletRequest.getTokenFromRequest(): String =
    this.getHeader(AuthInterceptor.NAME_AUTHORIZATION_HEADER)
        .replace("Bearer ", "")


fun Game.toGameSerialized(): GameSerialized {
    return GameSerialized(
        gameId = this.id,
        name = this.name,
        board = this.board.serialize(),
        state = this.state.stateChar,
        turn = this.currentTurn.color
    )
}

fun GameSerialized.toGame(): Game {
    return Game(
        id = this.gameId,
        board = Board.deserialize(this.board),
        name = name,
        state = this.state.toGameState(),
        currentTurn = this.turn.toPieceColor()
    )
}


fun Game.toGameOut(): GameOut {
    return GameOut(
        id = this.id,
        name = this.name,
        currentTurn = this.currentTurn.color,
        pieces = this.board.serialize(),
        state = if (this.state == ACTIVE) "Game is Active" else "Game is Finished, winner: ${this.currentTurn}"
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

const val emptySquare = "| "
const val blackPiece = "|B"
const val whitePiece = "|W"
val boardLine = "--".repeat(BOARD_DIM) + " \n"

fun Game.gameString(): String {
    val game = this
    var boardString = ""
    boardString +=
        "GAME: ${game.name} IS ${if (game.state == ACTIVE) "ACTIVE" else "FINISHED"} \n "

    repeat(BOARD_DIM) { row ->
        boardString += boardLine
        repeat(BOARD_DIM) { col ->
            val piece = game.board.pieces.find { it.position == Position(row, col) }
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