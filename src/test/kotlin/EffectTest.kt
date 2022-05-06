import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test

class EffectTest {
    private val fluentEmpty = mockk<Fluent>(relaxed = false)
    private val effectEmpty = mockkClass(Effect::class){
        every{ fluent } returns mockk<Fluent>(relaxed = false)
        every { isPositive } returns false
    }
    private val effectNotEmpty = mockkClass(Effect::class){
        every{ fluent } returns mockk<Fluent>(relaxed = true)
        every { isPositive } returns true
    }
    @Test
    fun testEffectEmptyCreation() {
        effectEmpty.fluent shouldBe fluentEmpty
        effectEmpty.isPositive shouldBe false
    }
    @Test
    fun testEffectNotEmptyCreation() {
        effectEmpty.fluent shouldNotBe fluentEmpty
        effectEmpty.isPositive shouldBe true
    }
}