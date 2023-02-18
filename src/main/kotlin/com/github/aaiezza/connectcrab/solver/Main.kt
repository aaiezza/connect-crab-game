package com.github.aaiezza.connectcrab.solver

import com.github.aaiezza.connectcrab.BoardPrinter
import com.github.aaiezza.connectcrab.Crab
import com.github.aaiezza.connectcrab.Move

fun main(args: Array<String>) {
    val consoleInterface = ConsoleInterface()

    consoleInterface.start()

    println("Thank you for using the Connect Crab Solver")
}

class ConsoleInterface(
    private val solver: Solver = Solver()
) {
    fun start() {
        val player = solver.board.indexedCrabs().first { it.crab.playerId.id == "Ψ" }.crab.playerId
        val opponent = solver.board.indexedCrabs().first { it.crab.playerId.id == "Θ" }.crab.playerId
        println("Assuming the default starting board and that you will go first as player ${player}.")

        var suggestedMoveAndState = solver.nextMoveFor(player)

        while (suggestedMoveAndState.first != null) {
            BoardPrinter.print(solver.board)
            suggestedMoveAndState.first?.let {
                println("Here is your next move:")
                println(it)
                solver.applyMove(it).also(BoardPrinter::print)
            }

            var opponentMove: Move? = null
            getOpponentMove@ while(suggestedMoveAndState.second.isInProgress && opponentMove == null) {
                try {
                    println("Type your opponent's Θ move (<crab#> <direction>):")
                    opponentMove = readln().split(" ").let {
                        Move(Crab(opponent, it[0].toUInt()), Move.Direction.valueOfCode(it[1]))
                    }
                    suggestedMoveAndState = solver.acceptOpponentMoveAndSuggestMove(opponentMove, player)
                } catch (e: Throwable) {
                    e.message?.also { eprintln(it) }
                    opponentMove = null
                    continue@getOpponentMove
                }
            }
        }

        println(suggestedMoveAndState.second.gameStateText)
        BoardPrinter.print(solver.board)
    }
}

fun eprintln(string: String) = System.err.println(string)
