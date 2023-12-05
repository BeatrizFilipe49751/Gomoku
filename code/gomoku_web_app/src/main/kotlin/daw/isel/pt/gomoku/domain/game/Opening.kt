package daw.isel.pt.gomoku.domain.game

enum class Opening(val id: Int) {
    FREESTYLE(1),
    PRO(2),
    LONG_PRO(3),
    SWAP(4)
}

fun Int.toOpening(): Opening {
    val values = Opening.values()
    check(this in 1..values.last().id)
    return values.first { it.id == this }
}

