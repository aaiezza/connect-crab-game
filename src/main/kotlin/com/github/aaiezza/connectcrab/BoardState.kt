package com.github.aaiezza.connectcrab

data class BoardState private constructor(
    val winningPlayer: Player?,
    val isDraw: Boolean
) {
    val gameStateText =
        if (isDraw) "Game is a draw"
        else if (winningPlayer != null) "Player $winningPlayer has won!"
        else "The game is in progress"

    val isTerminalState = isDraw || winningPlayer != null

    val isInProgress = !isTerminalState

    init {
        require(
            !isDraw || (isDraw && this.winningPlayer == null)
        ) { "If it is a draw, there can be no winner. This state would not make sense and is illegal." }
    }

    constructor(isDraw: Boolean) : this(null, isDraw)
    constructor(winningPlayerId: Player) : this(winningPlayerId, false)
}
