import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isTrue
import com.github.aaiezza.connectcrab.*
import com.github.aaiezza.connectcrab.Move.Direction.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MoveFinderTests {
    private lateinit var subject: MoveFinder

    @BeforeEach
    fun setUp() {
        subject = MoveFinder()
    }

    @Test
    fun `should find expected possible moves`() {
        val b = MutableList(6) { MutableList<Crab?>(6) { null } }

        val Θ = Player("Θ")
        val Ψ = Player("Ψ")

        b[0][0] = Crab(Θ, 1u)
        b[0][2] = Crab(Ψ, 1u)
        b[0][5] = Crab(Ψ, 2u)
        b[2][0] = Crab(Ψ, 3u)
        b[2][5] = Crab(Θ, 3u)
        b[3][0] = Crab(Θ, 2u)

        val board = ConnectCrabBoard(b)

        BoardPrinter.print(board)

        val responseΨ = subject.invoke(board, Ψ)

        assertThat(responseΨ).containsExactly(
            Move(Crab(Ψ, 1u), RIGHT),
            Move(Crab(Ψ, 1u), DOWN),
            Move(Crab(Ψ, 1u), LEFT),
            Move(Crab(Ψ, 2u), DOWN),
            Move(Crab(Ψ, 2u), LEFT),
            Move(Crab(Ψ, 3u), UP),
            Move(Crab(Ψ, 3u), RIGHT),
        )

        val responseΘ = subject.invoke(board, Θ)

        assertThat(responseΘ).containsExactly(
            Move(Crab(Θ, 1u), RIGHT),
            Move(Crab(Θ, 1u), DOWN),
            Move(Crab(Θ, 2u), RIGHT),
            Move(Crab(Θ, 2u), DOWN),
            Move(Crab(Θ, 3u), UP),
            Move(Crab(Θ, 3u), DOWN),
            Move(Crab(Θ, 3u), LEFT),
        )
    }
}