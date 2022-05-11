import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.res.fluentEmpty
import resources.res.fluentNotEmpty
import resources.res.nameGC
import resources.res.predicateEmpty
import resources.res.predicateNotEmpty

class FluentTest {
    @Test
    fun testEmptyCreation() {
        fluentEmpty.name shouldBe ""
        fluentEmpty.args.isEmpty() shouldBe true
        (fluentEmpty.instanceOf == predicateEmpty) shouldBe true
        fluentEmpty.isNegated shouldBe false
    }
    @Test
    fun testNotEmptyCreation() {
        fluentNotEmpty.name shouldBe nameGC
        fluentNotEmpty.args.isEmpty() shouldBe false
        (fluentNotEmpty.instanceOf == predicateNotEmpty) shouldBe true
        fluentNotEmpty.isNegated shouldBe true
    }
}