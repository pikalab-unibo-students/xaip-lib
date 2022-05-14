import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import it.unibo.tuprolog.core.TermFormatter
import org.junit.Test
import resources.Res.effectEmpty
import resources.Res.effectNotEmpty
import resources.Res.fluentEmpty
import resources.Res.fluentNotEmpty

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