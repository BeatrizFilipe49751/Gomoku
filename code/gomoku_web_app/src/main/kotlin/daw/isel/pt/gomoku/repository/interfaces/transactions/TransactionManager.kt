package daw.isel.pt.gomoku.repository.interfaces.transactions

interface TransactionManager {
    fun <R> run(block: (Transaction) -> R): R
}