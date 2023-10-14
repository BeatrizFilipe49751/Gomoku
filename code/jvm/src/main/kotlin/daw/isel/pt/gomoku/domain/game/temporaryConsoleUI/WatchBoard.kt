package daw.isel.pt.gomoku.domain.game.temporaryConsoleUI

import daw.isel.pt.gomoku.domain.game.BOARD_DIM
import daw.isel.pt.gomoku.domain.game.Board
import daw.isel.pt.gomoku.domain.game.Game
import daw.isel.pt.gomoku.domain.game.GameState.*
import daw.isel.pt.gomoku.domain.game.Piece
import daw.isel.pt.gomoku.domain.game.PieceColor.*
import daw.isel.pt.gomoku.domain.game.Position
import java.util.*

/**
 * Temporary console testing class
 */
data class WatchBoard (val game: Game) {
    private val emptySquare = "| "
    private val blackPiece = "|B"
    private val whitePiece = "|W"
    private val boardLine = "--".repeat(BOARD_DIM)

    fun printBoard() {
        println(
            "GAME: ${game.name} IS ${if (game.state == ACTIVE) "ACTIVE" else "FINISHED"}"
        )
        repeat(BOARD_DIM) { col ->
            println(boardLine)
            repeat(BOARD_DIM) { row ->
                val piece = game.board.pieces.find { it.position == Position(row, col) }
                if (piece != null)
                    if (piece.color == BLACK) print(blackPiece)
                    else print(whitePiece)
                else print("$emptySquare")
            }
            print("|")
            println()
        }
        println(boardLine)
        if (game.state == FINISHED)
            println("WINNER: ${game.currentTurn}")
    }
}

/**
 * VERY HARD-CODED, will be implemented as unit test
 */
fun main() {
    val testGame = Game(UUID.randomUUID(), Board(1), "testGame", ACTIVE)
    var watchBoard = WatchBoard(testGame)
    watchBoard.printBoard()
    watchBoard = WatchBoard(testGame.play(Piece(Position(0, 0), BLACK)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(0,1), WHITE)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(1,1), BLACK)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(0,2), WHITE)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(2,2), BLACK)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(0,3), WHITE)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(3,3), BLACK)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(0,6), WHITE)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(4,4), BLACK)))
    println("NEW BOARD INCOMING --------------------")
    watchBoard.printBoard()
    // this will throw an error as the game is already finished
    watchBoard = WatchBoard(watchBoard.game.play(Piece(Position(0,5), WHITE)))
}