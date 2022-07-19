import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.actions
import resources.TestUtils.actionEmpty
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.name
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.substitution
import resources.TestUtils.type1
import resources.TestUtils.variableNotEmpty
import resources.TestUtils.variables
import resources.TestUtils.types
import resources.TestUtils.Actions
import resources.TestUtils.Values
import resources.TestUtils.Types
import resources.TestUtils.Fluents


class ActionTest : AnnotationSpec() {
    private val variable = Variable.of("different value")
    private val substitution2 = VariableAssignment.of(variableNotEmpty, variable)
    private val fluent = Fluent.of(
        predicateNotEmpty, true, List<Value>(size) { variable }
    )
    private val action = Action.of(
        name,
        mapOf(variableNotEmpty to type1),
        setOf(fluent),
        setOf(Effect.of(fluent, true))
    )

    @Test
    fun testEmptyCreation() {
        actionEmpty.name.isEmpty() shouldBe true
        actionEmpty.parameters.isEmpty() shouldBe true
        actionEmpty.preconditions.isEmpty() shouldBe true
        actionEmpty.effects.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmptyCreation() {
        actionNotEmpty.name shouldBe name
        actionNotEmpty.parameters.isEmpty() shouldNotBe true
        actionNotEmpty.parameters.forEach { it.value shouldBe type1 }
        actionNotEmpty.preconditions.isEmpty() shouldNotBe true
        actionNotEmpty.effects.isEmpty() shouldNotBe true
    }

    @Test
    fun testApplyWorksAsExpected() {
        actionNotEmpty.apply(substitution) shouldBe actionNotEmpty
        actionNotEmpty.apply(substitution2) === action
    }

    @Test
    fun testRefreshVariableWorksAsExpected() {
        actionNotEmpty == actionNotEmpty
        actionNotEmpty shouldNotBe actionNotEmpty.refresh()
    }

    @Test
    fun testActionObjectWorksAsExpected() {
        Actions.pick shouldBeIn actions

        Actions.pick.parameters.isEmpty() shouldNotBe true
        Actions.pick.parameters.forEach { it.key shouldBeIn variables }
        Actions.pick.parameters.forEach { it.value shouldBeIn types }

        Actions.pick.preconditions.isEmpty() shouldNotBe true
        Actions.pick.effects.isEmpty() shouldNotBe true

        Actions.pick.apply(VariableAssignment.of(Values.X, Values.X)) shouldBe
                Action.of(
                    "pick", mapOf(
                    Values.X to Types.blocks),
                    setOf(Fluents.atXFloor, Fluents.armEmpty, Fluents.clearX),
                    setOf(
                        Effect.of(Fluents.atXArm),
                        Effect.negative(Fluents.atXFloor),
                        Effect.negative(Fluents.armEmpty),
                        Effect.negative(Fluents.clearX)
                    )
                )
        Actions.pick.apply(VariableAssignment.of(Values.X, Values.Y)) ===
                Action.of(
                    "pick", mapOf(
                        Values.X to Types.blocks),
                    setOf(Fluents.atYFloor, Fluents.armEmpty, Fluents.clearY),
                    setOf(
                        Effect.of(Fluents.atYArm),
                        Effect.negative(Fluents.atYFloor),
                        Effect.negative(Fluents.armEmpty),
                        Effect.negative(Fluents.clearY)
                    )
                )
    }
}