import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.BlockWorldDomain
import resources.BlockWorldDomain.Effects
import resources.BlockWorldDomain.Fluents
import resources.BlockWorldDomain.Predicates
import resources.BlockWorldDomain.Values
import resources.BlockWorldDomain.VariableAssignments
import resources.BlockWorldDomain.effectEmpty
import resources.BlockWorldDomain.effectNotEmpty
import resources.BlockWorldDomain.fluentEmpty
import resources.BlockWorldDomain.fluentNotEmpty
import resources.BlockWorldDomain.substitution

class EffectTest : AnnotationSpec() {
    private val variable = Variable.of("different value")

    private val substitution2 = VariableAssignment.of(BlockWorldDomain.variableNotEmpty, variable)
    private val fluent = Fluent.of(
        BlockWorldDomain.predicateNotEmpty,
        true,
        List<Value>(BlockWorldDomain.size) { variable }
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
