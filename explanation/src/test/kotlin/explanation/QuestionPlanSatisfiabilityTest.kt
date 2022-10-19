package explanation

import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.putdownA
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionPlanSatisfiability
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class QuestionPlanSatisfiabilityTest : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())

    @Test
    fun `BlockWorld plan valid`() {
        val q5 = QuestionPlanSatisfiability(Problems.pickX, Plan.of(listOf(pickA)))
        val explanation = Explanation.of(q5, explainer)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `BlockWorld plan not valid`() {
        val q5 = QuestionPlanSatisfiability(
            Problems.pickX,
            Plan.of(listOf(pickA, putdownA))
        )
        val explanation = Explanation.of(q5, explainer)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe false
    }

    @Test
    fun `GraphDomain plan valid`() {
        val q5 = QuestionPlanSatisfiability(Problems.pickX, Plan.of(listOf(pickA)))
        val explanation = Explanation.of(q5, explainer)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `GraphDomain plan not valid`() {
        val q5 = QuestionPlanSatisfiability(
            Problems.pickX,
            Plan.of(listOf(pickA, putdownA))
        )
        val explanation = Explanation.of(q5, explainer)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe false
    }
}
