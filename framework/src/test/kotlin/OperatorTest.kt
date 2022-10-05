import domain.BlockWorldDomain.Actions
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Types
import domain.BlockWorldDomain.Values
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.name
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.substitution
import resources.TestUtils.type1
import resources.TestUtils.variableNotEmpty

class OperatorTest : AnnotationSpec() {
    private val variable = Variable.of("different value")
    private val substitution2 =
        VariableAssignment.of(variableNotEmpty, variable)
    private val fluent = Fluent.of(
        predicateNotEmpty,
        true,
        List<Value>(size) { variable }
    )
    private val action = Action.of(
        name,
        mapOf(variableNotEmpty to type1),
        setOf(fluent),
        setOf(Effect.of(fluent, true))
    )

    @Test
    fun testApplyWorksAsExpected() {
        Operator.of(actionNotEmpty).apply(substitution) shouldBe Operator.of(actionNotEmpty)
    }

    @Test
    fun testActionObjectVariableAssignmentX2XworksAsExpected() {
        Operator.of(Actions.pick).apply(VariableAssignment.of(Values.X, Values.X)) shouldBe
            Operator.of(
                Action.of(
                    "pick",
                    mapOf(
                        Values.X to Types.blocks
                    ),
                    setOf(Fluents.armEmpty, Fluents.clearX, Fluents.atXFloor),
                    setOf(
                        Effect.of(Fluents.atXArm),
                        Effect.negative(Fluents.armEmpty),
                        Effect.negative(Fluents.clearX),
                        Effect.negative(Fluents.atXFloor)
                    )
                )
            )
    }

    @Test
    fun testEqualsWithDifferentParameters() {
        Operator.of(actionNotEmpty).apply(substitution2) shouldNotBe Operator.of(action)

        Operator.of(Actions.pick).apply(VariableAssignment.of(Values.X, Values.Y)) shouldNotBe
            Operator.of(
                Action.of(
                    "pick",
                    mapOf(
                        Values.X to Types.blocks
                    ),
                    setOf(Fluents.armEmpty, Fluents.clearY),
                    setOf(
                        Effect.of(Fluents.atYArm),
                        Effect.negative(Fluents.armEmpty),
                        Effect.negative(Fluents.clearY),
                        Effect.negative(Fluents.atYFloor)
                    )
                )
            )
    }
}
