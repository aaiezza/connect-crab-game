package com.github.aaiezza.connectcrab

import com.github.aaiezza.connectcrab.ConnectCrabBoard.Companion.IndexedCrab
import com.github.aaiezza.connectcrab.Move.Direction.UP
import com.github.aaiezza.connectcrab.Move.Direction.RIGHT
import com.github.aaiezza.connectcrab.Move.Direction.DOWN
import com.github.aaiezza.connectcrab.Move.Direction.LEFT

class MoveApplier(
    val boardStateCalculator: BoardStateCalculator
) {
    operator fun invoke(board: ConnectCrabBoard, move: Move): ConnectCrabBoard {
        require(
            !boardStateCalculator(board, move.crab.playerId).isTerminalState
        ) { "Board state is terminal, so a move cannot be applied." }

        val indexedCrabToMove = board.indexedCrabs().first { it.crab == move.crab }

        val newIndexedCrab: IndexedCrab = when(move.direction) {
            UP -> {
                val closestCrab = board.indexedCrabs()
                    .filter { it.col == indexedCrabToMove.col && it.row < indexedCrabToMove.row }
                    .maxByOrNull { it.row }
                require(
                    closestCrab?.let{ indexedCrabToMove.row - 1 > it.row } ?: (indexedCrabToMove.row > 0)
                ) { "Crab ${move.crab} cannot move ${move.direction}" }
                val newRow = if(closestCrab == null) 0 else closestCrab.row + 1

                IndexedCrab(move.crab.copy(), newRow, indexedCrabToMove.col)
            }
            RIGHT -> {
                val closestCrab = board.indexedCrabs()
                    .filter { it.row == indexedCrabToMove.row && it.col > indexedCrabToMove.col }
                    .minByOrNull { it.col }
                require(
                    closestCrab?.let{ indexedCrabToMove.col + 1 < it.col } ?: (indexedCrabToMove.col < board.maxColIndex)
                ) { "Crab ${move.crab} cannot move ${move.direction}" }
                val newCol = if(closestCrab == null) board.maxColIndex else closestCrab.col - 1

                IndexedCrab(move.crab.copy(), indexedCrabToMove.row, newCol)
            }
            DOWN -> {
                val closestCrab = board.indexedCrabs()
                    .filter { it.col == indexedCrabToMove.col && it.row > indexedCrabToMove.row }
                    .minByOrNull { it.row }
                require(
                    closestCrab?.let{ indexedCrabToMove.row + 1 < it.row } ?: (indexedCrabToMove.row < board.maxRowIndex)
                ) { "Crab ${move.crab} cannot move ${move.direction}" }
                val newRow = if(closestCrab == null) board.maxRowIndex else closestCrab.row - 1

                IndexedCrab(move.crab.copy(), newRow, indexedCrabToMove.col)
            }
            LEFT -> {
                val closestCrab = board.indexedCrabs()
                    .filter { it.row == indexedCrabToMove.row && it.col < indexedCrabToMove.col }
                    .maxByOrNull { it.col }
                require(
                    closestCrab?.let{ indexedCrabToMove.col - 1 > it.col } ?: (indexedCrabToMove.col > 0)
                ) { "Crab ${move.crab} cannot move ${move.direction}" }
                val newCol = if(closestCrab == null) 0 else closestCrab.col + 1

                IndexedCrab(move.crab.copy(), indexedCrabToMove.row, newCol)
            }
        }

        val newBoard = MutableList(6) { MutableList<Crab?>(6) { null } }

        board.indexedCrabs()
            .forEach {
                if(it.crab != move.crab) {
                    newBoard[it.row][it.col] = it.crab
                }
            }
        newBoard[newIndexedCrab.row][newIndexedCrab.col] = newIndexedCrab.crab

        return ConnectCrabBoard(newBoard)
    }
}

