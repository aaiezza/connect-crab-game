package com.github.aaiezza.connectcrab

class ConnectCrabGame(
    val board: ConnectCrabBoard,
    val players: List<Player>
) {
    val boardStateCalculator = BoardStateCalculator(MoveFinder())

    val moveApplier = MoveApplier(boardStateCalculator)

    fun ConnectCrabBoard.state(player: Player) = boardStateCalculator(this, player.id)
    fun playGame(print: Boolean): Triple<ConnectCrabBoard, BoardState, UInt> {
        val result = players.cycle()
            .runningFoldIndexed(Triple(board, board.state(players.first()), 0U)) { turnI, b, currentPlayer ->
                var board = b.first

                if (print) {
                    println("Turn #${turnI}")
                    BoardPrinter.print(board)
                }

                if (b.second.isInProgress) {
                    board = currentPlayer.makeMove(board).first?.let {
                        if (print) println(it)
                        moveApplier(board, it)
                    } ?: board
                }

                Triple(board, board.state(currentPlayer), turnI.toUInt())
            }.first { it.second.isTerminalState }

        if (print) {
            BoardPrinter.print(result.first)
            println(result.second.gameStateText)
        }

        return result
    }
}

fun <T> Sequence<T>.cycle(): Sequence<T> =
    generateSequence(this) { this }.flatten()

fun <T> List<T>.cycle(): Sequence<T> =
    this.asSequence().cycle()
