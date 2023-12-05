package daw.isel.pt.gomoku.repository.interfaces.transactions

import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import daw.isel.pt.gomoku.repository.interfaces.LobbyRepository
import daw.isel.pt.gomoku.repository.interfaces.UserRepository

interface Transaction {

    val usersRepository: UserRepository
    val lobbyRepository: LobbyRepository
    val gameRepository: GameRepository
    // other repository types
    fun rollback()
}