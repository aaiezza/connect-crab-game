package com.github.aaiezza.connectcrab

import kotlin.text.StringBuilder

class BoardPrinter {
    operator fun invoke(board: ConnectCrabBoard): String {
        val out = StringBuilder()
        board.value.forEach {
            it.forEachIndexed { col, crab ->
                out.append("│")
                out.append(crab?.let { "$crab" } ?: "  ")
                if (col == board.maxColIndex) {
                    out.append("│\n")
                }
            }
        }
        return out.toString()
    }

    companion object {
        fun print(board: ConnectCrabBoard): Unit {
            println(BoardPrinter().invoke(board))
        }
    }
}