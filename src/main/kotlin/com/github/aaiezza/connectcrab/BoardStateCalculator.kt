package com.github.aaiezza.connectcrab

class BoardStateCalculator(
    private val moveFinder: MoveFinder
) {
    operator fun invoke(board: ConnectCrabBoard, playerId: String) =
        invoke(board, board.indexedCrabs().first { it.crab.player.id == playerId }.crab.player)

    operator fun invoke(board: ConnectCrabBoard, currentPlayer: Player): BoardState {
        val players = board.indexedCrabs().map { it.crab.player }.distinct()
        val winner = players.firstOrNull {  player ->
            val winningColFoursome = board.indexedCrabs()
                .filter { it.crab.player == player }
                .groupBy { it.col }
                .filterValues { it.size >= 4 }
                .mapNotNull { colCrabs ->
                    colCrabs.value
                        .sortedBy { it.row }
                        .windowed(4, 1)
                        .mapNotNull { foursome ->
                            if (foursome.windowed(2, 1)
                                .sumOf { it[1].row - it[0].row } == 3
                            ) foursome else null
                        }.firstOrNull { it.isNotEmpty() }
                }

            val winningRowFoursome = board.indexedCrabs()
                .filter { it.crab.player == player }
                .groupBy { it.row }
                .filterValues { it.size >= 4 }
                .mapNotNull { rowCrabs ->
                    rowCrabs.value
                        .sortedBy { it.col }
                        .windowed(4, 1)
                        .mapNotNull { foursome ->
                            if (foursome.windowed(2, 1)
                                    .sumOf { it[1].col - it[0].col } == 3
                            ) foursome else null
                        }.firstOrNull { it.isNotEmpty() }
                }

            winningColFoursome.isNotEmpty() || winningRowFoursome.isNotEmpty()
        }

        return if (winner != null) {
            BoardState(winner)
        } else if(moveFinder.invoke(board, currentPlayer).isEmpty()) {
            BoardState(true)
        } else {
            BoardState(false)
        }
    }
}