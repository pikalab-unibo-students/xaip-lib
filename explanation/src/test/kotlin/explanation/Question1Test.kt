package explanation

import explanation.impl.Question1
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.Operators
import resources.domain.BlockWorldDomain.Planners
import resources.domain.BlockWorldDomain.Problems

class Question1Test : AnnotationSpec() {
    @Test
    fun `Use pickA instead of pick B in armNotEmpty problem`() {
        val q1 = Question1(
            Problems.armNotEmpty,
            Plan.of(listOf(Operators.pickB)),
            Operators.pickA,
            0
        )

        val hPlan = Planners.stripsPlanner.plan(q1.buildHproblem()).first()
        val explanation = Explanation.of(q1.plan, hPlan, q1)

        val contrastiveExplanation = Explanation.of(
            q1.plan,
            Plan.of(listOf(Operators.pickA)),
            q1
        )
        explanation shouldBe contrastiveExplanation
        explanation.isPlanValid() shouldBe true
    }
}
