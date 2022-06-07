import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Predicates
import resources.TestUtils.axiomEmpty
import resources.TestUtils.axiomNotEmpty
import resources.TestUtils.Types
import resources.TestUtils.Axioms
import resources.TestUtils.Values
import resources.TestUtils.Fluents

class AxiomTest : AnnotationSpec() {

    @Test
    fun testEmptyCreation() {
        axiomEmpty.parameters.isEmpty() shouldBe true
        axiomEmpty.context.isEmpty() shouldBe true
        axiomEmpty.implies.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmptyCreation() {
        axiomNotEmpty.parameters.isEmpty() shouldNotBe true
        axiomNotEmpty.context.isEmpty() shouldNotBe true
        axiomNotEmpty.implies.isEmpty() shouldNotBe true
    }

    @Test
    fun testAxiomObjectWorksAsExpected() {
        Axioms.axiom1.parameters.isEmpty() shouldNotBe true
        Axioms.axiom1.context.isEmpty() shouldNotBe true
        Axioms.axiom1.implies.isEmpty() shouldNotBe true

        Axioms.axiom1.parameters.forEach { it.value shouldBe Types.blocks }
        Axioms.axiom1.parameters.forEach { it.key shouldBeIn arrayOf(Values.Y, Values.X) }

        Axioms.axiom1.context.forEach{ it.isNegated shouldBe false}
        Axioms.axiom1.context.forEach{ it.isGround shouldBe false}
        Axioms.axiom1.context.forEach{ it.instanceOf shouldBeIn arrayOf(Predicates.on, Predicates.at)}
        Axioms.axiom1.context.forEach { it.args shouldBeIn arrayOf(Fluents.onXY.args, Fluents.atXFloor.args)}

        Axioms.axiom1.implies.forEach{ it.isNegated shouldBe false}
        Axioms.axiom1.implies.forEach { it.isGround shouldBe false }
        Axioms.axiom1.implies.forEach { it.instanceOf shouldBe Predicates.clear }
        Axioms.axiom1.implies.forEach { it.args shouldBe Fluents.clearY.args }
    }
}