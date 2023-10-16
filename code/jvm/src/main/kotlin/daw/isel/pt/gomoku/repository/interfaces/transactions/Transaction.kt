package daw.isel.pt.gomoku.repository.interfaces.transactions

import daw.isel.pt.gomoku.repository.interfaces.LobbyRepository
import daw.isel.pt.gomoku.repository.interfaces.UserRepository

interface Transaction {

    val usersRepository: UserRepository
    val lobbyRepository: LobbyRepository

    // other repository types
    fun rollback()
}