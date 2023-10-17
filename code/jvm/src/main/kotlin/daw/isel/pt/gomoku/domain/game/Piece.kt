package daw.isel.pt.gomoku.domain.game

data class Piece(val position: Position, val color: PieceColor) {

    companion object {
        fun deserialize(p: String): Piece {
            val params = p.split(":")
            check(params.size == 3) {"$params Piece Parameter size wrong"}
            val pos = Position.deserialize("${params[0]}:${params[1]}")
            val color = params[2].first().toPieceColor()
            return Piece(pos, color)
        }
    }

    override fun toString() = "$position:${color.color}"
}