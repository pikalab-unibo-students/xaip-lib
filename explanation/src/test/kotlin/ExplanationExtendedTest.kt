import domain.BlockWorldDomain
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.putdownB
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.unstackAB
import domain.BlockWorldDomain.Problems
import explanation.Explanation
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
        explanationExtended.idempotentList() shouldBe emptyMap()
    }

    @Test
    fun `Test incorrect plan`() {
        val problem = Problems.armNotEmpty
        val planPickB = Plan.of(listOf(BlockWorldDomain.Operators.pickB))
        val planPickBstackBApickC = Plan.of(listOf(BlockWorldDomain.Operators.pickB, BlockWorldDomain.Operators.stackBA, BlockWorldDomain.Operators.pickC))

        val q4 = QuestionPlanProposal(problem, planPickBstackBApickC, planPickB, planPickB.operators.first(), 0)

        val explanation = Explanation.of(q4.plan, q4.alternativePlan, q4)

        val explanationExtended = ExplanationExtended(explanation)
        explanationExtended.isPlanLengthAcceptable() shouldBe true
        explanationExtended.isProblemSolvable() shouldBe true
        explanationExtended.isIdempotentActions(pickB, putdownB) shouldBe true
        explanationExtended.idempotentList() shouldBe emptyMap()
    }
}
