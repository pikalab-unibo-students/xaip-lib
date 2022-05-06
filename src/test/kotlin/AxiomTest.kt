import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test

class AxiomTest {
    private val axiomEmpty = mockkClass(Axiom::class){
        every { parameters } returns emptyMap()
        every{ context} returns emptySet()
        every{ implies} returns emptySet()
    }
    private val axiomNotEmpty = mockkClass(Axiom::class){
        every {parameters } returns mockk(relaxed =true)
        every{context} returns mockk( relaxed = true)
        every{implies} returns mockk( relaxed = true)
    }

    @Test
    fun testAxiomEmptyCreation() {
        axiomEmpty.parameters.isEmpty() shouldBe true
        axiomEmpty.context.isEmpty() shouldBe true
        axiomEmpty.implies.isEmpty() shouldBe true
    }
    @Test
    fun testActionNotEmptyCreation() {
        axiomNotEmpty.parameters.isEmpty() shouldNotBe true
        axiomNotEmpty.context.isEmpty() shouldNotBe true
        axiomNotEmpty.implies.isEmpty() shouldNotBe true
    }
}