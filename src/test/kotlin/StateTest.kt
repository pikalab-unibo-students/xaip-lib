import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test
import resources.TestUtils.actionEmpty
import resources.TestUtils.actionNotEmpty

class StateTest {
    private val stateEmpty: State = mockkClass(State::class){
        every { fluents } returns emptySet()
        every { isApplicable(actionEmpty)} returns false
        //every { apply(state) of State} returns action
    }
    private val stateNotEmpty: State = mockkClass(State::class){
        every { fluents } returns mockk(relaxed =true)
        every { isApplicable(actionNotEmpty)} returns true
        //every { apply(state)} returns action
    }
    @Test
    fun testEmptyCreation() {
        stateEmpty.fluents.isEmpty() shouldBe true
        stateEmpty.isApplicable(actionEmpty) shouldBe false
        //stateEmpty.apply(action) shouldBe state
    }

    @Test
    fun testNotEmptyCreation() {
        stateNotEmpty.fluents.isEmpty() shouldBe false
        stateNotEmpty.isApplicable(actionNotEmpty) shouldBe true
        //assertEquals(stateEmpty.apply(action), state)
    }
}