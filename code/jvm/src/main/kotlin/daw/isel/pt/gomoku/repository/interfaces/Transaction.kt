package daw.isel.pt.gomoku.repository.interfaces

interface Transaction {

    val usersRepository: UserRepository

    // other repository types
    fun rollback()
}