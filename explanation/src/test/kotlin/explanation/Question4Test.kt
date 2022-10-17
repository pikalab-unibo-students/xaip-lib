package explanation

import domain.BlockWorldDomain.Operators
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionPlanProposal
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class Question4Test : AnnotationSpec() {
    private val problem = Problems.armNotEmpty
    private val planPickBStackBA = Plan.of(listOf(Operators.pickB, Operators.stackBA))
    private val planPickB = Plan.of(listOf(Operators.pickB))
    private val planPickBstackBApickC = Plan.of(listOf(Operators.pickB, Operators.stackBA, Operators.pickC))
    private val explainer = Explainer.of(Planner.strips())

    @Test
    fun `Test valid plan`() {
        val q4 = QuestionPlanProposal(problem, planPickBstackBApickC, planPickB, planPickB.operators.first(), 0)
        val explanation = Explanation.of(q4, explainer)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Test incorrect plan`() {
        val q4 = QuestionPlanProposal(problem, planPickBStackBA, planPickB, planPickB.operators.first(), 0)
        val explanation = Explanation.of(q4, explainer)
        explanation.isPlanValid() shouldBe false
    }
}
