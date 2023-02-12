package com.github.aaiezza.connectcrab

data class Crab(val player: Player, val id: UInt) {
    constructor(playerId: String, id: UInt) : this(Player(playerId), id)

    override fun toString() = "$player$id"
}