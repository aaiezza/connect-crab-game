package com.github.aaiezza.connectcrab

class BoardStateCalculator(
    private val moveFinder: MoveFinder
) {
    operator fun invoke(board: ConnectCrabBoard, playerId: String) =
        invoke(board, board.indexedCrabs().first { it.crab.playerId.id == playerId }.crab.playerId)

    operator fun invoke(board: ConnectCrabBoard, currentPlayerId: PlayerId): BoardState {
        val players = board.indexedCrabs().map { it.crab.playerId }.distinct()
        val winner = players.firstOrNull {  player ->
            val winningColFoursome = getFoursome(board, player, ConnectCrabBoard.Companion.IndexedCrab::col, ConnectCrabBoard.Companion.IndexedCrab::row)

            val winningRowFoursome = getFoursome(board, player, ConnectCrabBoard.Companion.IndexedCrab::row, ConnectCrabBoard.Companion.IndexedCrab::col)

            winningColFoursome.isNotEmpty() || winningRowFoursome.isNotEmpty()
        }

        return if (winner != null) {
            BoardState(winner)
        } else if(moveFinder.invoke(board, currentPlayerId).isEmpty()) {
            BoardState(true)
        } else {
            BoardState(false)
        }
    }

    private fun getFoursome(
        board: ConnectCrabBoard,
        player: PlayerId,
        groupBy: (ConnectCrabBoard.Companion.IndexedCrab) -> Int,
        by: (ConnectCrabBoard.Companion.IndexedCrab) -> Int):
            List<List<ConnectCrabBoard.Companion.IndexedCrab>> =
        board.indexedCrabs()
            .filter { it.crab.playerId == player }
            .groupBy { groupBy }
            .filterValues { it.size >= 4 }
            .mapNotNull { rowCrabs ->
                rowCrabs.value
                    .sortedBy { by(it) }
                    .windowed(4, 1)
                    .mapNotNull { foursome ->
                        if (foursome.windowed(2, 1)
                                .sumOf { by(it[1]) - by(it[0]) } == 3
                        ) foursome else null
                    }.firstOrNull { it.isNotEmpty() }
            }

    companion object {
        fun calculateState(board: ConnectCrabBoard, currentPlayerId: PlayerId) =
            BoardStateCalculator(MoveFinder()).invoke(board, currentPlayerId)
    }
}