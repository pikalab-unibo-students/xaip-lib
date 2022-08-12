import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Axioms
import resources.TestUtils.Fluents
import resources.TestUtils.Predicates
import resources.TestUtils.Types
import resources.TestUtils.Values

class AxiomTest : AnnotationSpec() {

    @Test
    fun testAxiomObjectWorksAsExpected() {
        Axioms.axiom1.parameters.isEmpty() shouldNotBe true
        Axioms.axiom1.parameters.forEach { it.value shouldBe Types.blocks }
        Axioms.axiom1.parameters.forEach { it.key shouldBeIn setOf(Values.Y, Values.X) }

        (Axioms.axiom1.context as Fluent).isNegated shouldBe false
        (Axioms.axiom1.context as Fluent).isGround shouldBe false
        (Axioms.axiom1.context as Fluent).instanceOf shouldBeIn arrayOf(Predicates.on, Predicates.at)
        Axioms.axiom1.context as Fluent shouldBe Fluents.atXArm

        (Axioms.axiom1.implies as Fluent).isNegated shouldBe false
        (Axioms.axiom1.implies as Fluent).isGround shouldBe false
        (Axioms.axiom1.implies as Fluent) shouldBe Fluents.onXY
    }
}
