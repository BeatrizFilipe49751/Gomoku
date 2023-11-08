package daw.isel.pt.gomoku.domain.game

enum class Variant(val id: Int) {
    FREESTYLE(1),
    SWAP(2),
}

fun Int.toVariant(): Variant {
    val values = Variant.values()
    check(this in 1..values.last().id)
    return values.first { it.id == this }
}