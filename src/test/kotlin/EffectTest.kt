import io.kotest.matchers.shouldBe
import kotlin.test.Test
import resources.TestUtils.effectEmpty
import resources.TestUtils.effectNotEmpty
import resources.TestUtils.fluentEmpty
import resources.TestUtils.fluentNotEmpty

class EffectTest {

    @Test
    fun testEmptyCreation() {
        effectEmpty.fluent shouldBe fluentEmpty
        effectEmpty.isPositive shouldBe false
    }
    @Test
    fun testNotEmptyCreation() {
        effectNotEmpty.fluent shouldBe fluentNotEmpty
        effectNotEmpty.isPositive shouldBe true
    }
}