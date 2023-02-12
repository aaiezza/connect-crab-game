package com.github.aaiezza.connectcrab

import java.lang.IllegalArgumentException

data class Move(val crab: Crab, val direction: Direction) {
    enum class Direction(
        val rowDelta: Int,
        val colDelta: Int
    ) {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

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