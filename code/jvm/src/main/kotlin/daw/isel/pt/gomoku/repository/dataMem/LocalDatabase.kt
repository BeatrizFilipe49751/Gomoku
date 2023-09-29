package daw.isel.pt.gomoku.repository.dataMem

import daw.isel.pt.gomoku.domain.Game
import daw.isel.pt.gomoku.domain.User

object LocalDatabase {
    var nextUserID = 0
    val users = mutableSetOf<User>()
    val games = mutableSetOf<Game>()
    val gameUsers = mutableSetOf<Pair<Game, User>>()

    fun reset() {
        users.removeAll { true }
        games.removeAll { true }
        gameUsers.removeAll { true }
        nextUserID = 0
    }
}

