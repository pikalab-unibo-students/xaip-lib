import domain.BlockWorldDomain.Effects
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Predicates
import domain.BlockWorldDomain.Values
import domain.BlockWorldDomain.VariableAssignments
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.effectEmpty
import resources.TestUtils.effectNotEmpty
import resources.TestUtils.fluentEmpty
import resources.TestUtils.fluentNotEmpty
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.substitution
import resources.TestUtils.variableNotEmpty

class EffectTest : AnnotationSpec() {
    private val variable = Variable.of("different value")

    private val substitution2 = VariableAssignment.of(variableNotEmpty, variable)
    private val fluent = Fluent.of(
        predicateNotEmpty,
        true,
        List<Value>(size) { variable }
    )

    private val effect = Effect.of(fluent, true)

    @Test
    fun testEmptyCreation() {
        effectEmpty.fluent shouldBe fluentEmpty
        effectEmpty.isPositive shouldBe false
    }

    @Test
    fun testNotEmptyCreation() {
        effectNotEmpty.fluent shouldBe fluentNotEmpty
        effectNotEmpty.isPositive shouldBe true
    }

    @Test
    fun testApplyWorksAsExpected() {
        effectNotEmpty.apply(substitution) shouldBe effectNotEmpty
        effectNotEmpty.apply(substitution2) shouldBe effect
    }

    @Test
    fun testEffectObjectWorksAsExpected() {
        Effects.atXFloor.fluent shouldBe Fluents.atXFloor
        Effects.atXFloor.isPositive shouldBe true
        Effects.atXFloor.apply(VariableAssignments.x2arm) shouldBe
            Effect.of(Fluent.of(Predicates.at, false, Values.arm, Values.floor))
    }

    @Test
    fun testRefreshWorksAsExpected() {
        effectNotEmpty shouldBe effectNotEmpty
        effectNotEmpty shouldNotBe effectNotEmpty.refresh()
    }
}
