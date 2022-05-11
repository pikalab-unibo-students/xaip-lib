import impl.EffectImpl
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Test
import resources.res.effectEmpty
import resources.res.effectNotEmpty
import resources.res.fluentEmpty
import resources.res.fluentNotEmpty

class EffectTest {

    @Test
    fun testEmptyCreation() {
        effectEmpty.fluent shouldBe fluentEmpty
        effectEmpty.isPositive shouldBe false
    }
    @Test
    fun testNotEmptyCreation() {
        effectNotEmpty.fluent shouldNotBe fluentNotEmpty
        effectNotEmpty.isPositive shouldBe true
    }
}