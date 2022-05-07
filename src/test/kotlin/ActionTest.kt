import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import resources.res.nameGC
import kotlin.test.Test

class ActionTest {
    private val actionEmpty = mockkClass(Action::class){
        every{ name } returns ""
        every { parameters } returns emptyMap()
        every{ preconditions} returns emptySet()
        every{ effects} returns emptySet()
    }
    private val actionNotEmpty = mockkClass(Action::class){
        every{ name } returns nameGC
        every {parameters } returns mockk(relaxed =true)
        every{preconditions} returns mockk( relaxed = true)
        every{effects} returns mockk( relaxed = true)
    }

    @Test
    fun testEmptyCreation() {
        actionEmpty.name shouldBe ""
        actionEmpty.parameters.isEmpty() shouldBe true
        actionEmpty.preconditions.isEmpty() shouldBe true
        actionEmpty.effects.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmptyCreation() {
        actionNotEmpty.name shouldBe nameGC
        actionNotEmpty.parameters.isEmpty() shouldNotBe true
        actionNotEmpty.preconditions.isEmpty() shouldNotBe true
        actionNotEmpty.effects.isEmpty() shouldNotBe true
    }
}