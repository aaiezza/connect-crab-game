package com.github.aaiezza.connectcrab

data class Move(val crab: Crab, val direction: Direction) {
    enum class Direction(
        val colDelta: Int,
        val rowDelta: Int
    ) {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

        override fun toString() = name[0] + ""
    }

    override fun toString() = "$crab $direction"
}