package com.github.aaiezza.connectcrab.player

import com.github.aaiezza.connectcrab.*

class RandomPlayer(
    override val id: PlayerId,
    override val moveFinder: MoveFinder
) : Player(id, moveFinder) {
    constructor(id: PlayerId) : this(id, MoveFinder())
    override fun determineMove(board: ConnectCrabBoard): Move {
        return moveFinder(board, this.id).shuffled().first()
    }
}