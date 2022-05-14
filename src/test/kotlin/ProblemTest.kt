import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Test
import resources.Res.domainEmpty
import resources.Res.domainNotEmpty
import resources.Res.objectSetEmpty
import resources.Res.objectSetNotEmpty
import resources.Res.problemEmpty
import resources.Res.problemNotEmpty
import resources.Res.state

class ProblemTest {
    @Test
    fun testEmptyCreation() {
        problemEmpty.domain shouldBe domainEmpty
        problemEmpty.objects shouldBe objectSetEmpty
        //problemEmpty.initialState.fluents.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmptyCreation() {
        problemNotEmpty.domain shouldBe domainNotEmpty
        problemNotEmpty.objects shouldBe objectSetNotEmpty
        problemNotEmpty.initialState shouldBe state
        //problemEmpty.initialState.fluents.isNotEmpty() shouldBe true
    }
}