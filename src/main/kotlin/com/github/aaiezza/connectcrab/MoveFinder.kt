package com.github.aaiezza.connectcrab

class MoveFinder {
    operator fun invoke(board: ConnectCrabBoard, playerId: String) =
        invoke(board, board.indexedCrabs().first { it.crab.playerId.id == playerId }.crab.playerId)

    operator fun invoke(board: ConnectCrabBoard, playerId: PlayerId): List<Move> {
        return board.indexedCrabs()
            .filter { it.crab.playerId == playerId }
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