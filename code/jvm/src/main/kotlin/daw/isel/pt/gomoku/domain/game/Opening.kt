package daw.isel.pt.gomoku.domain.game

import java.lang.IllegalStateException

enum class Opening(val id: Int) {
    PRO(1),
    LONG_PRO(2),
    SWAP(3),
}

fun Int.toOpening(): Opening {
    val values = Opening.values()
    check(this in 1..values.last().id)
    return values.first { it.id == this }
}

