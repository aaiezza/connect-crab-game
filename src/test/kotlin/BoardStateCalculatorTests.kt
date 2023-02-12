import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.github.aaiezza.connectcrab.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardStateCalculatorTests {
    private lateinit var subject: BoardStateCalculator

    private val moveFinder = MoveFinder()
    private val Θ = Player("Θ")
    private val Ψ = Player("Ψ")

    @BeforeEach
    fun setUp() {
        subject = BoardStateCalculator(moveFinder)
    }

    @Test
    fun `should find an in progress board state`() {
        val b = MutableList(6) { MutableList<Crab?>(6) { null } }

        b[0][0] = Crab(Ψ, 1u)
        b[1][0] = Crab(Ψ, 2u)
        b[2][0] = Crab(Ψ, 3u)
        b[2][5] = Crab(Θ, 1u)
        b[4][0] = Crab(Ψ, 4u)
        b[5][0] = Crab(Ψ, 5u)

        val board = ConnectCrabBoard(b)

        BoardPrinter.print(board)

        val response = subject.invoke(board, Θ)

        assertThat(response.isInProgress).isTrue()
    }

    @Test
    fun `should find a winning board state col`() {
        val b = MutableList(6) { MutableList<Crab?>(6) { null } }

        b[0][0] = Crab(Ψ, 1u)
        b[1][0] = Crab(Ψ, 2u)
        b[2][0] = Crab(Ψ, 3u)
        b[2][5] = Crab(Θ, 1u)
        b[3][0] = Crab(Ψ, 4u)
        b[5][0] = Crab(Ψ, 5u)

        val board = ConnectCrabBoard(b)

        BoardPrinter.print(board)

        val response = subject.invoke(board, Θ)

        assertThat(response).isEqualTo(BoardState(Ψ))
    }

    @Test
    fun `should find a winning board state col _ 2`() {
        val b = MutableList(6) { MutableList<Crab?>(6) { null } }

        b[0][0] = Crab(Θ, 1u)
        b[0][1] = Crab(Θ, 5u)
        b[0][2] = Crab(Ψ, 1u)
        b[0][3] = Crab(Θ, 4u)
        b[0][4] = Crab(Θ, 3u)
        b[0][5] = Crab(Ψ, 2u)
        b[1][0] = Crab(Θ, 2u)
        b[1][5] = Crab(Ψ, 3u)
        b[2][5] = Crab(Ψ, 5u)
        b[3][5] = Crab(Ψ, 4u)
        b[4][5] = Crab(Θ, 6u)
        b[5][0] = Crab(Ψ, 6u)

        val board = ConnectCrabBoard(b)

        BoardPrinter.print(board)

        val response = subject.invoke(board, Θ)

        assertThat(response).isEqualTo(BoardState(Ψ))
    }

    @Test
    fun `should find a winning board state row`() {
        val b = MutableList(6) { MutableList<Crab?>(6) { null } }

        b[1][1] = Crab(Ψ, 1u)
        b[1][2] = Crab(Ψ, 2u)
        b[1][3] = Crab(Ψ, 3u)
        b[5][3] = Crab(Θ, 1u)
        b[1][4] = Crab(Ψ, 4u)
        b[3][2] = Crab(Ψ, 5u)

        val board = ConnectCrabBoard(b)

        BoardPrinter.print(board)

        val response = subject.invoke(board, Θ)

        assertThat(response).isEqualTo(BoardState(Ψ))
    }
}