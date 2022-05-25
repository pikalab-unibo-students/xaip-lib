import io.kotest.matchers.shouldBe
import kotlin.test.Test
import resources.TestUtils.fluentEmpty
import resources.TestUtils.fluentNotEmpty
import resources.TestUtils.name
import resources.TestUtils.predicateEmpty
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.variableNotEmpty

class FluentTest {

    @Test
    fun testEmptyCreation() {
        fluentEmpty.name.isEmpty() shouldBe true
        fluentEmpty.args.isEmpty() shouldBe true
        (fluentEmpty.instanceOf == predicateEmpty) shouldBe true
        fluentEmpty.isNegated shouldBe false
    }
    @Test
    fun testNotEmptyCreation() {
        fluentNotEmpty.name shouldBe name
        fluentNotEmpty.args.isEmpty() shouldBe false
        fluentNotEmpty.args.size shouldBe size
        fluentNotEmpty.args.forEach{it shouldBe variableNotEmpty}
        (fluentNotEmpty.instanceOf == predicateNotEmpty) shouldBe true
        fluentNotEmpty.isNegated shouldBe true
    }
}