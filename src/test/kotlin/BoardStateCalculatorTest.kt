import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.github.aaiezza.connectcrab.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardStateCalculatorTest {
    private lateinit var subject: BoardStateCalculator

    private val moveFinder = MoveFinder()

    @BeforeEach
    fun setUp() {
        subject = BoardStateCalculator(moveFinder)
    }

    @Test
    fun `should find an in progress board state`() {
        val b = MutableList(6) { MutableList<Crab?>(6) { null } }

        b[0][0] = Crab("Ψ", 1u)
        b[1][0] = Crab("Ψ", 2u)
        b[2][0] = Crab("Ψ", 3u)
        b[2][5] = Crab("Θ", 1u)
        b[4][0] = Crab("Ψ", 4u)
        b[5][0] = Crab("Ψ", 5u)

        val board = ConnectCrabBoard(b)

        BoardPrinter.print(board)

        val response = subject.invoke(board, Player("Θ"))

        assertThat(response.isInProgress).isTrue()
    }

    @Test
    fun `should find a winning board state col`() {
        val b = MutableList(6) { MutableList<Crab?>(6) { null } }

        b[0][0] = Crab("Ψ", 1u)
        b[1][0] = Crab("Ψ", 2u)
        b[2][0] = Crab("Ψ", 3u)
        b[2][5] = Crab("Θ", 1u)
        b[3][0] = Crab("Ψ", 4u)
        b[5][0] = Crab("Ψ", 5u)

        val board = ConnectCrabBoard(b)

        BoardPrinter.print(board)

        val response = subject.invoke(board, Player("Θ"))

        assertThat(response).isEqualTo(BoardState(Player("Ψ")))
    }

    @Test
    fun `should find a winning board state row`() {
        val b = MutableList(6) { MutableList<Crab?>(6) { null } }

        b[1][1] = Crab("Ψ", 1u)
        b[1][2] = Crab("Ψ", 2u)
        b[1][3] = Crab("Ψ", 3u)
        b[5][3] = Crab("Θ", 1u)
        b[1][4] = Crab("Ψ", 4u)
        b[3][2] = Crab("Ψ", 5u)

        val board = ConnectCrabBoard(b)

        BoardPrinter.print(board)

        val response = subject.invoke(board, Player("Θ"))

        assertThat(response).isEqualTo(BoardState(Player("Ψ")))
    }
}