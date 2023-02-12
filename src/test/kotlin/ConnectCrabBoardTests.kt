import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNotZero
import com.github.aaiezza.connectcrab.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ConnectCrabBoardTests {
    private lateinit var subject: ConnectCrabBoard

    private val moveFinder = MoveFinder()
    private val boardStateCalculator = BoardStateCalculator(moveFinder)
    private val moveApplier = MoveApplier(boardStateCalculator)
    private val boardPrinter = BoardPrinter()

    @BeforeEach
    fun setUp() {
        subject = ConnectCrabBoard()
    }

    @Test
    fun `should make move and yield new board`() {
        println(boardPrinter.invoke(subject))

        val moves: List<Move> = moveFinder(subject, Player("Ψ"))

        moves.forEach { println(it) }
        println()

        assertThat(moves.size).isEqualTo(12)

        val response: ConnectCrabBoard = moveApplier(subject, moves.first())

        assertThat(response).isNotNull()
        println(boardPrinter.invoke(response))

        val boardState = boardStateCalculator.invoke(response, Player("Θ"))
        println(boardState)
    }
}
