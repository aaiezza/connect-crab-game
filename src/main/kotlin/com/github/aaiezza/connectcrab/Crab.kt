package com.github.aaiezza.connectcrab

data class Crab(val playerId: PlayerId, val id: UInt) {
    constructor(playerId: String, id: UInt) : this(PlayerId(playerId), id)

    override fun toString() = "$playerId$id"
}