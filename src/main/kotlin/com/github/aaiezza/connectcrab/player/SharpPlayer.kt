package com.github.aaiezza.connectcrab.player

import com.github.aaiezza.connectcrab.*

class SharpPlayer(
    override val id: PlayerId,
    override val moveFinder: MoveFinder
) : Player(id, moveFinder) {
    constructor(id: PlayerId) : this(id, MoveFinder())

    operator fun ConnectCrabBoard.plus(move: Move) = MoveApplier(boardStateCalculator).invoke(this, move)
    override fun determineMove(board: ConnectCrabBoard): Move {
        val possibleMoves = moveFinder(board, this.id)
        var move: Move?

        // If you can win in one move, take that move!
        move = possibleMoves.firstOrNull {
            boardStateCalculator(board + it, this.id)?.winningPlayerId == this.id
        }

        // If you can stop your opponent from winning, take that move!
        if (move == null) {
            val opponentPlayer = board.indexedCrabs().first { it.crab.playerId != this.id }.crab.playerId
            moveFinder(board, opponentPlayer).firstOrNull {
                boardStateCalculator(board + it, this.id)?.winningPlayerId == opponentPlayer
            }?.also {
                move = possibleMoves.firstOrNull { nextMove ->
                    val nextBoard = board + nextMove
                    moveFinder(nextBoard, opponentPlayer).none {
                        boardStateCalculator(nextBoard + it, this.id)?.winningPlayerId == opponentPlayer
                    }
                }
            }
        }

        // Choose a random move
        if (move == null)
            move = possibleMoves.shuffled().first()

        return move!!
    }
}