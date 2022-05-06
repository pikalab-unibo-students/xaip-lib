import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test
import resources.res.action

class StateTest {
    private val stateEmpty: State = mockkClass(State::class){
        every { fluents } returns emptySet()
        every { isApplicable(action)} returns false
        //every { apply(state)} returns action
    }
    private val stateNotEmpty: State = mockkClass(State::class){
        every { fluents } returns mockk(relaxed =true)
        every { isApplicable(action)} returns true
        //every { apply(state)} returns action
    }
    @Test
    fun testActionEmptyCreation() {
        stateEmpty.fluents.isEmpty() shouldBe true
        stateEmpty.isApplicable(action) shouldBe false
        //stateEmpty.apply(action) shouldBe state
    }

    @Test
    fun testActionNotEmptyCreation() {
        stateNotEmpty.fluents.isEmpty() shouldBe false
        stateNotEmpty.isApplicable(action) shouldBe true
        //assertEquals(stateEmpty.apply(action), state)
    }
}