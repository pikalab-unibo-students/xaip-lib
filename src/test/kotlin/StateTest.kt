import io.kotest.matchers.shouldBe
import resources.TestUtils
import kotlin.test.Test
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.effectNotEmpty
import resources.TestUtils.name

class StateTest {
    private val fluent = Fluent.of(
        name,
        emptyList(),
        TestUtils.predicateNotEmpty,
        true
    )

    private val action= Action.of(
        name,
        mapOf(TestUtils.variableNotEmpty to TestUtils.type1),
        setOf(fluent),
        setOf(effectNotEmpty
    ))

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
        state.isApplicable(action) shouldBe true
        //assertEquals(stateEmpty.apply(action), state)
    }
}