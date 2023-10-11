package daw.isel.pt.gomoku.domain.game


const val BOARD_DIM = 15
const val LAST_INDEX = BOARD_DIM - 1
const val LAST_COL_CHAR = 'a' + LAST_INDEX
const val MAX_POSITIONS = BOARD_DIM * BOARD_DIM
class Board(val positions : List<Position>){
}