import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import resources.TestUtils
import kotlin.test.Test
import resources.TestUtils.actionEmpty
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.fluentEmpty
import resources.TestUtils.fluentNotEmpty

class StateTest {
    private val fluent = Fluent.of(
        TestUtils.name,
        emptyList(),
        TestUtils.predicateNotEmpty, true
    )

    private val state = State.of(setOf(fluent))

    private val stateEmpty: State = State.of(emptySet())

    @Test
    fun testEmptyCreation() {
        stateEmpty.fluents.isEmpty() shouldBe true
        stateEmpty.isApplicable(actionNotEmpty) shouldBe false
        //stateEmpty.apply(action) shouldBe state
    }

    @Test
    fun testNotEmptyCreation() {
        state.fluents.isEmpty() shouldBe false
        state.isApplicable(actionNotEmpty) shouldBe true
        //assertEquals(stateEmpty.apply(action), state)
    }
}