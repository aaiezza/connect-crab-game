package com.github.aaiezza.connectcrab

abstract class Player private constructor (
    open val id: PlayerId,
    protected open val moveFinder: MoveFinder,
    protected val boardStateCalculator: BoardStateCalculator
) {
    protected constructor (id: PlayerId, moveFinder: MoveFinder) : this(id, moveFinder, BoardStateCalculator(moveFinder))
    fun makeMove(board: ConnectCrabBoard): Pair<Move?, BoardState> {
        val boardState = boardStateCalculator(board, this.id)

        if (boardState.isTerminalState) {
            return Pair(null, boardState)
        }

        return Pair(determineMove(board), boardState)
    }

    protected abstract fun determineMove(board: ConnectCrabBoard): Move
}