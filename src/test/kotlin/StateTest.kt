import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import kotlin.test.Test
import resources.TestUtils.actionEmpty
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.fluentEmpty
import resources.TestUtils.fluentNotEmpty

class StateTest {
    private val state = State.of( setOf<Fluent>(fluentEmpty))

    private val stateEmpty: State = mockkClass(State::class){
        every { fluents } returns emptySet()
        every { isApplicable(actionEmpty)} returns false
        //every { apply(state) of State} returns action
    }
    /*
    private val stateNotEmpty: State = mockkClass(State::class){
        every { fluents } returns emptySet()
        every { isApplicable(actionEmpty)} returns false
        //every { apply(state) of State} returns action
    }

     */
    //private val stateNotEmpty: State = mockkClass(State::class){
        //every { fluents } returns mockk(relaxed =true)
        //every { isApplicable(actionEmpty)} returns true
        //every { apply(state)} returns action
    //}

    @Test
    fun testEmptyCreation() {
        stateEmpty.fluents.isEmpty() shouldBe true
        stateEmpty.isApplicable(actionEmpty) shouldBe false
        //stateEmpty.apply(action) shouldBe state
    }

    @Test
    fun testNotEmptyCreation() {
        state.fluents.isEmpty() shouldBe false
        //state.isApplicable(actionNotEmpty) shouldBe false
        //assertEquals(stateEmpty.apply(action), state)
    }
}