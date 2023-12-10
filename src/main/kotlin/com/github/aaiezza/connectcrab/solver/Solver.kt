package com.github.aaiezza.connectcrab.solver

import com.github.aaiezza.connectcrab.*

class Solver(
    initialBoard: ConnectCrabBoard = ConnectCrabBoard(),
    private val moveFinder: MoveFinder = MoveFinder(),
    private val boardStateCalculator: BoardStateCalculator = BoardStateCalculator(moveFinder),
    private val moveApplier: MoveApplier = MoveApplier(boardStateCalculator)
) {
    operator fun ConnectCrabBoard.plus(move: Move) = moveApplier.invoke(this, move)

    var board: ConnectCrabBoard = initialBoard
        private set

    fun acceptOpponentMoveAndSuggestMove(opponentMove: Move, nextPlayerId: PlayerId): Pair<Move?, BoardState> {
        board += opponentMove
        return nextMoveFor(nextPlayerId)
    }

    fun applyMove(move: Move): ConnectCrabBoard {
        board += move
        return board
    }

    fun nextMoveFor(playerId: PlayerId): Pair<Move?, BoardState> {
        val boardState = boardStateCalculator.invoke(board, playerId)

        if (boardState.isTerminalState) {
            return Pair(null, boardState)
        }

        val possibleMoves = moveFinder(board, playerId)
        var move: Move?

        // If you can win in one move, take that move!
        move = possibleMoves.firstOrNull {
            boardStateCalculator(board + it, playerId).winningPlayerId == playerId
        }

        // If you can stop your opponent from winning, take that move!
        if (move == null) {
            val opponentPlayer = board.indexedCrabs().first { it.crab.playerId != playerId }.crab.playerId
            moveFinder(board, opponentPlayer).firstOrNull {
                boardStateCalculator(board + it, playerId).winningPlayerId == opponentPlayer
            }?.also {
                move = possibleMoves.firstOrNull { nextMove ->
                    val nextBoard = board + nextMove
                    moveFinder(nextBoard, opponentPlayer).none {
                        boardStateCalculator(nextBoard + it, playerId).winningPlayerId == opponentPlayer
                    }
                }
            }
        }

        // Choose a random move
        if (move == null)
            move = possibleMoves.shuffled().first()

        return Pair(move, boardState)
    }
}