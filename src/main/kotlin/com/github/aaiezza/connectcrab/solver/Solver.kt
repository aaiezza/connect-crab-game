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

    fun acceptOpponentMoveAndSuggestMove(opponentMove: Move, nextPlayer: Player): Pair<Move?, BoardState> {
        board += opponentMove
        return nextMoveFor(nextPlayer)
    }

    fun applyMove(move: Move): ConnectCrabBoard {
        board += move
        return board
    }

    fun nextMoveFor(player: Player): Pair<Move?, BoardState> {
        val boardState = boardStateCalculator.invoke(board, player)

        if (boardState.isTerminalState) {
            return Pair(null, boardState)
        }

        val possibleMoves = moveFinder(board, player)
        var move: Move?

        // If you can win in one move, take that move!
        move = possibleMoves.firstOrNull {
            boardStateCalculator(board + it, player)?.winningPlayer == player
        }

        // If you can stop your opponent from winning, take that move!
        if (move == null) {
            val opponentPlayer = board.indexedCrabs().first { it.crab.player != player }.crab.player
            moveFinder(board, opponentPlayer).firstOrNull {
                boardStateCalculator(board + it, player)?.winningPlayer == opponentPlayer
            }?.also {
                move = possibleMoves.firstOrNull { nextMove ->
                    val nextBoard = board + nextMove
                    moveFinder(nextBoard, opponentPlayer).none {
                        boardStateCalculator(nextBoard + it, player)?.winningPlayer == opponentPlayer
                    }
                }
            }
        }

        // Choose a random move
        if (move == null)
            move = moveFinder.invoke(board, player).shuffled().first()

        return Pair(move, boardState)
    }
}