package explanation

import Plan
import domain.BlockWorldDomain
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.putdownB
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackBA
import domain.BlockWorldDomain.Operators.unstackAB
import domain.BlockWorldDomain.Problems
import explanation.impl.ExplanationExtended
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionPlanProposal
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class ExplanationExtendedTest : AnnotationSpec() {
    @Test
    fun `Test correct plan`() {
        val q1 = QuestionAddOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(pickB)),
            pickA,
            0
        )

        val hypotheticalPlan =
            BlockWorldDomain.Planners.stripsPlanner.plan(q1.buildHypotheticalProblem().first()).first()
        val explanation = Explanation.of(q1.plan, hypotheticalPlan, q1)

        val explanationExtended = ExplanationExtended(explanation)
        explanationExtended.isPlanLengthAcceptable() shouldBe true
        explanationExtended.isProblemSolvable() shouldBe true
        explanationExtended.isIdempotentActions(stackAB, unstackAB) shouldBe true
        explanationExtended.idempotentList().contains(pickA) shouldBe true
    }

    @Test
    fun `Test incorrect plan`() {
        val problem = Problems.armNotEmpty
        val planPickB = Plan.of(listOf(pickB, putdownB))
        val planPickBstackBApickC =
            Plan.of(listOf(pickB, stackBA, pickC))

        val q4 = QuestionPlanProposal(problem, planPickB, planPickBstackBApickC, planPickB.operators.first(), 0)

        val explanation = Explanation.of(q4.plan, q4.alternativePlan, q4)

        val explanationExtended = ExplanationExtended(explanation)
        explanationExtended.isPlanLengthAcceptable() shouldBe true
        explanationExtended.isProblemSolvable() shouldBe true
        explanationExtended.isIdempotentActions(pickB, putdownB) shouldBe true
        explanationExtended.idempotentList().contains(pickB)
        explanationExtended.idempotentList()[pickB]!!.occurence1 shouldBe 1
        explanationExtended.idempotentList()[pickB]!!.operator2 shouldBe putdownB
        explanationExtended.idempotentList()[pickB]!!.occurence2 shouldBe 1
    }
}
