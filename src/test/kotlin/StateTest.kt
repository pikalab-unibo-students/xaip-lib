import impl.StateImpl
import io.kotest.matchers.shouldBe
import org.junit.Ignore
import resources.TestUtils
import resources.TestUtils.actionEmpty
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.effectNotEmpty
import resources.TestUtils.fluentEmpty
import resources.TestUtils.name
import resources.TestUtils.state
import resources.TestUtils.substitution
import resources.TestUtils.type1
import resources.TestUtils.variableNotEmpty
import kotlin.test.Test

class StateTest {
    private val stateEmpty: State = State.of(setOf(fluentEmpty))

    private val fluent = Fluent.of(
        name,
        listOf(variableNotEmpty),
        TestUtils.predicateNotEmpty,
        true
    )

    private val state1 = State.of(setOf(fluent))

    private val action = Action.of(
        name,
        mapOf(variableNotEmpty to type1),
        setOf(fluent),
        setOf(effectNotEmpty)
    )

    @Test
    @Ignore
    fun testEmptyCreation() {
        stateEmpty.fluents.isEmpty() shouldBe false
    }

    @Test
    @Ignore
    fun testNotEmptyCreation() {
        state.fluents.isEmpty() shouldBe false
        state.fluents.forEach { it.isGround shouldBe true }
    }

    @Test
    @Ignore
    fun testApplyWorksAsExpected() {
        state.apply(substitution) shouldBe state
        /*
            finisci il caso di sostituzione sensata:
        */
    }

    @Test
    @Ignore
    fun testIsApplicableWorkAsExpected() {
        state.isApplicable(actionNotEmpty) shouldBe false
        state.isApplicable(actionEmpty) shouldBe true
        state1.isApplicable(action) shouldBe true
    }

    @Test
    @Ignore
    fun testMguForActionPreconditionsAsSequenceWorkAsExpected() {
        val fluent1: Fluent = Fluent.of(
            name,
            List<Value>(TestUtils.size) { variableNotEmpty },
            TestUtils.predicateNotEmpty, true
        )

        val action = Action.of(
            name,
            mapOf(variableNotEmpty to type1),
            setOf(fluent1),
            setOf(effectNotEmpty)
        )

        val stateTemp = StateImpl(setOf(fluent1))

        stateTemp.mguForActionPreconditionsSet(action) shouldBe true
    }
}
