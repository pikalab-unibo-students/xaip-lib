import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.mockkObject
import kotlin.test.Test

class ActionCreation {
    private val nameGC ="Giovanni"
    private val actionEmpty = mockkClass(Action::class){
        every{ name } returns ""
        every { parameters } returns emptyMap()
        every{ preconditions} returns emptySet()
        every{ effects} returns emptySet()
    }
    private val action = mockkClass(Action::class){
        every{ name } returns nameGC
        every {parameters } returns mockk(relaxed =true)
        every{preconditions} returns mockk( relaxed = true)
        every{effects} returns mockk( relaxed = true)
    }

    @Test
    fun testActionEmptyCreation() {
        actionEmpty.name shouldBe ""
        actionEmpty.parameters.isEmpty() shouldBe true
        actionEmpty.preconditions.isEmpty() shouldBe true
        actionEmpty.effects.isEmpty() shouldBe true
    }
    @Test
    fun testActionNotEmptyCreation() {
        action.name shouldBe nameGC
        action.parameters.isEmpty() shouldNotBe true
        action.preconditions.isEmpty() shouldNotBe true
        action.effects.isEmpty() shouldNotBe true
    }
}