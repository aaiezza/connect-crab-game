package com.github.aaiezza.connectcrab

class MoveFinder {
    operator fun invoke(board: ConnectCrabBoard, playerId: String) =
        invoke(board, board.indexedCrabs().first { it.crab.player.id == playerId }.crab.player)

    operator fun invoke(board: ConnectCrabBoard, player: Player): List<Move> {
        return board.indexedCrabs()
            .filter { it.crab.player == player }
            .sortedBy { it.crab.id }
            .flatMap { crab ->
                Move.Direction.values().mapNotNull { dir ->
                    val row = crab.row + dir.rowDelta
                    val col = crab.col + dir.colDelta
                    if (row in 0..board.maxRowIndex && col in 0..board.maxColIndex) {
                        if (board.value[row][col] == null) Move(crab.crab, dir) else null
                    } else null
                }
            }.toList()
    }
}