package explanation

import domain.BlockWorldDomain.Operators
import domain.BlockWorldDomain.Planners
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionAddOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class Question1Test : AnnotationSpec() {
    @Test
    fun `Use pickA instead of pick B in armNotEmpty problem`() {
        val q1 = QuestionAddOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(Operators.pickB)),
            Operators.pickA,
            0
        )

        val hypotheticalPlan = Planners.stripsPlanner.plan(q1.buildHypotheticalProblem().first()).first()
        val explanation = Explanation.of(q1.plan, hypotheticalPlan, q1)

        val contrastiveExplanation = Explanation.of(
            q1.plan,
            Plan.of(listOf(Operators.pickA)),
            q1
        )
        explanation shouldBe contrastiveExplanation
        explanation.isPlanValid() shouldBe true
    }
}
