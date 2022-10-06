package explanation

import Operator
import Plan
import VariableAssignment
import explanation.impl.Question2
import explanation.oldVersion.ExplanationUtils
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values
class Question2Test : AnnotationSpec() {
    @Test
    fun `Remove pickA from the plan to solve the armNotEmpty problem`() {
        val q2 = Question2(
            Problems.armNotEmpty,
            Plan.of(listOf(pickA)),
            pickA,
            0
        )

        val hplan = stripsPlanner.plan(q2.buildHproblem()).first()
        val explanation = Explanation.of(q2.plan, hplan, q2)

        val contrastiveExplanation = Explanation.of(
            q2.plan,
            Plan.of(listOf(pickB)),
            q2
        )
        explanation shouldBe contrastiveExplanation
        explanation.isPlanValid() shouldBe true
    }
}
