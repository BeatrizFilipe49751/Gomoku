package daw.isel.pt.gomoku.repository.interfaces

import org.springframework.stereotype.Component

interface TransactionManager {
    fun <R> run(block: (Transaction) -> R): R
}