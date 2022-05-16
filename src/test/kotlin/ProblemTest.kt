import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Test
import resources.TestUtils.domainEmpty
import resources.TestUtils.domainNotEmpty
import resources.TestUtils.objectSetEmpty
import resources.TestUtils.objectSetNotEmpty
import resources.TestUtils.problemEmpty
import resources.TestUtils.problemNotEmpty
import resources.TestUtils.state

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