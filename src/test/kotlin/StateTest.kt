import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.actionEmpty
import kotlin.test.Test
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.effectNotEmpty
import resources.TestUtils.name
import resources.TestUtils.objNotEmpty
import resources.TestUtils.state
import resources.TestUtils.substitution

class StateTest {
    private val fluent = Fluent.of(
        name,
        listOf(objNotEmpty),
        TestUtils.predicateNotEmpty,
        true
    )
    val state1= State.of(setOf(fluent))

    private val action= Action.of(
        name,
        mapOf(TestUtils.variableNotEmpty to TestUtils.type1),
        setOf(fluent),
        setOf(effectNotEmpty)
    )

    private val stateEmpty: State = State.of(emptySet())

    @Test
    fun testEmptyCreation() {
        stateEmpty.fluents.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmptyCreation() {
        state.fluents.isEmpty() shouldBe false
        state.fluents.forEach{it.isGround shouldBe true}
    }

    @Test
    fun testApplyWorksAsExpected(){
        state.apply(substitution) shouldBe state
        /*
        finisci il caso di sostituzione sensata:
        non capisco come conciliare che lo stato debba avere tutti i fluent ground
         ossia che non debbano contenere nessuna variabile con il fatto che per
         essere applicabile devo fare match tra le precondiizoni e i fluent dello stato
         */
    }

    @Test
    fun testIsApplicableWorkAsExpected(){
        state.isApplicable(actionNotEmpty) shouldBe false
        state.isApplicable(actionEmpty) shouldBe true
        state1.isApplicable(action) shouldBe true
    }
}