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

        if(boardState.isTerminalState) {
            return Pair(null, boardState)
        }

        // TODO: Implement some really intelligent logic here

        return Pair(moveFinder.invoke(board, player).first(), boardState)
    }
}