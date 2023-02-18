package com.github.aaiezza.connectcrab

data class BoardState private constructor(
    val winningPlayerId: PlayerId?,
    val isDraw: Boolean
) {
    val gameStateText =
        if (isDraw) "Game is a draw"
        else if (winningPlayerId != null) "Player $winningPlayerId has won!"
        else "The game is in progress"

    val isTerminalState = isDraw || winningPlayerId != null

    val isInProgress = !isTerminalState

    init {
        require(
            !isDraw || (isDraw && this.winningPlayerId == null)
        ) { "If it is a draw, there can be no winner. This state would not make sense and is illegal." }
    }

    constructor(isDraw: Boolean) : this(null, isDraw)
    constructor(winningPlayerIdId: PlayerId) : this(winningPlayerIdId, false)
}
