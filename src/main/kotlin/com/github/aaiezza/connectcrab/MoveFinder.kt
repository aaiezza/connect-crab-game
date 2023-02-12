package com.github.aaiezza.connectcrab

class MoveFinder {
    operator fun invoke(board: ConnectCrabBoard, playerId: String) =
        invoke(board, board.indexedCrabs().first { it.crab.player.id == playerId }.crab.player)

    operator fun invoke(board: ConnectCrabBoard, player: Player): List<Move> {
        return board.indexedCrabs()
            .filter { it.crab.player == player }
            .flatMap { crab ->
                Move.Direction.values().mapNotNull { dir ->
                    val col = crab.col + dir.colDelta
                    val row = crab.row + dir.rowDelta
                    if (col in 0..board.maxColIndex && row in 0..board.maxRowIndex) {
                        if (board.value[col][row] == null) Move(crab.crab, dir) else null
                    } else null
                }
            }.toList()
    }
}