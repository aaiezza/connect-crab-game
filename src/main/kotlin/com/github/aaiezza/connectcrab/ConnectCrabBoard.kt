package com.github.aaiezza.connectcrab

data class ConnectCrabBoard constructor(
    val value: List<List<Crab?>>
) {
    val maxColIndex = value[0].size - 1
    val maxRowIndex = value.size - 1

    fun indexedCrabs() =
        value.flatMapIndexed {
                row, crabRow ->
            crabRow.mapIndexedNotNull {
                    col, crab ->
                crab?.let { IndexedCrab(it, row, col) }
            }
        }

    init {
        // TODO: Put a bunch of requirements in here that would dictate a legal game board configuration
    }

    constructor() : this(createInitial6by6Board())

    companion object {
        data class IndexedCrab(
            val crab: Crab,
            val row: Int,
            val col: Int
        )

        private fun createInitial6by6Board(): List<List<Crab?>> {
            val board = MutableList(6) { MutableList<Crab?>(6) { null } }

            val Θ = Player("Θ")
            val Ψ = Player("Ψ")

            board[0][0] = Crab(Θ, 1u)
            board[0][2] = Crab(Ψ, 1u)
            board[0][3] = Crab(Θ, 2u)
            board[0][5] = Crab(Ψ, 2u)
            board[2][0] = Crab(Ψ, 3u)
            board[2][5] = Crab(Θ, 3u)
            board[3][0] = Crab(Θ, 4u)
            board[3][5] = Crab(Ψ, 4u)
            board[5][0] = Crab(Ψ, 5u)
            board[5][2] = Crab(Θ, 5u)
            board[5][3] = Crab(Ψ, 6u)
            board[5][5] = Crab(Θ, 6u)

            return board
        }
    }
}
