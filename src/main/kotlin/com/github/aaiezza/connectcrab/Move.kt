package com.github.aaiezza.connectcrab

import java.lang.IllegalArgumentException

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

        companion object {
            fun valueOfCode(value: String) =
                when(value.lowercase()) {
                    "u" -> UP
                    "r" -> RIGHT
                    "d" -> DOWN
                    "l" -> LEFT
                    else -> {
                        throw IllegalArgumentException("No match for direction $value")
                    }
                }
        }
    }

    override fun toString() = "$crab $direction"
}