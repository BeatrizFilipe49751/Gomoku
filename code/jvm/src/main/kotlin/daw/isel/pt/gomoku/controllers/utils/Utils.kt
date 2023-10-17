package daw.isel.pt.gomoku.controllers.utils

import daw.isel.pt.gomoku.controllers.models.GameSerialized
import daw.isel.pt.gomoku.controllers.models.UserOut
import daw.isel.pt.gomoku.controllers.models.UserOutWithToken
import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.domain.game.Board
import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.game.toGameState
import daw.isel.pt.gomoku.domain.game.toPieceColor


fun Game.toGameSerialized(): GameSerialized {
    return GameSerialized(
        id = this.id,
        name = this.name,
        board = this.board.serialize(),
        state = this.state.stateChar,
        turn = this.currentTurn.color
    )
}

fun GameSerialized.toGame(): Game {
    return Game(
        id = this.id,
        board = Board.deserialize(this.board),
        name = name,
        state = this.state.toGameState(),
        currentTurn = this.turn.toPieceColor()
    )
}
fun User.toUserOut(): UserOut{
    return UserOut(this.userId, this.username, this.email)
}

fun User.toUserOutWithToken(): UserOutWithToken{
    return UserOutWithToken(this.userId, this.username, this.token)
}
