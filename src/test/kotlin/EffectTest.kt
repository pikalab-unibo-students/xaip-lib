import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test
import resources.res.fluentEmpty

class EffectTest {
    private val effectEmpty = mockkClass(Effect::class){
        every{ fluent } returns fluentEmpty
        every { isPositive } returns false
    }
    private val effectNotEmpty = mockkClass(Effect::class){
        every{ fluent } returns mockk(relaxed = true)
        every { isPositive } returns true
    }
    @Test
    fun testEmptyCreation() {
        effectEmpty.fluent shouldBe fluentEmpty
        effectEmpty.isPositive shouldBe false
    }
    @Test
    fun testNotEmptyCreation() {
        effectNotEmpty.fluent shouldNotBe fluentEmpty
        effectNotEmpty.isPositive shouldBe true
    }
}