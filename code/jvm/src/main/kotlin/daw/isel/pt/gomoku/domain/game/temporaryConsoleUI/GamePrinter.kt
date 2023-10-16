package daw.isel.pt.gomoku.domain.game.temporaryConsoleUI

import daw.isel.pt.gomoku.domain.game.*
import daw.isel.pt.gomoku.domain.game.GameState.*
import daw.isel.pt.gomoku.domain.game.PieceColor.*
import java.util.*

/**
 * Temporary console preview testing file
 */

const val emptySquare = "| "
const val blackPiece = "|B"
const val whitePiece = "|W"
val boardLine = "--".repeat(BOARD_DIM)

fun printBoard(game: Game) {
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
            else print(emptySquare)
        }
        print("|")
        println()
    }
    println(boardLine)
    if (game.state == FINISHED)
        println("WINNER: ${game.currentTurn}")
}


/**
 * VERY HARD-CODED, will be implemented as unit test
 */
fun main() {
    var testGame = Game(UUID.randomUUID(), Board(1), "testGame", ACTIVE)
    while (true) {
        if (testGame.state == FINISHED)
            println("Game is finished, do not try to play")
        println("Type play position color")
        val command = readln()
        val args = command.split(" ")
        if (args.size == 1 && args[0] == "exit")
            break
        try {
            testGame = testGame.play(
                Piece(
                    Position(args[1].toInt(), args[2].toInt()),
                    args[3].first().toPieceColor()
                )
            )
            printBoard(testGame)
        } catch (ex: Exception) {
            println("Error: $ex")
        }
    }
}