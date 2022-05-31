import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.actionEmpty
import kotlin.test.Test
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.effectNotEmpty
import resources.TestUtils.name
import resources.TestUtils.state
import resources.TestUtils.substitution

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

    //private val state = State.of(setOf(fluent))

    private val stateEmpty: State = State.of(emptySet())

    @Test
    fun testEmptyCreation() {
        stateEmpty.fluents.isEmpty() shouldBe true
        stateEmpty.isApplicable(actionNotEmpty) shouldBe false
    }

    @Test
    fun testNotEmptyCreation() {
        state.fluents.isEmpty() shouldBe false
        state.isApplicable(action) shouldBe true
    }

    @Test
    fun testApplyWorksAsExpected(){
        state.apply(substitution) shouldBe state
        //finisci il caso di sostituzione sensata
    }

    @Test
    fun testIsApplicableWorkAsExpected(){
        state.isApplicable(actionNotEmpty) shouldBe false
        state.isApplicable(actionEmpty) shouldBe true
    }
}