import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.aaiezza.connectcrab.*
import com.github.aaiezza.connectcrab.player.RandomPlayer
import com.github.aaiezza.connectcrab.player.SharpPlayer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ConnectCrabGameTests {
    private lateinit var subject: ConnectCrabGame

    private val Θ = PlayerId("Θ")
    private val Ψ = PlayerId("Ψ")

    @BeforeEach
    fun setUp() {
        subject = ConnectCrabGame(ConnectCrabBoard(), listOf(SharpPlayer(Θ), SharpPlayer(Θ)))
    }

    @Test
    fun `should play game`() {
        val response = subject.playGame(print = true)

        assertThat(response.second.winningPlayerId).isEqualTo(Θ)
    }
}