package explanation

import core.Plan
import core.State
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Planners.stripsPlanner
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class Question3Test : AnnotationSpec() {

    @Test
    fun `Remove pickA from the plan to solve the armNotEmpty problem`() {
        val q3 = QuestionReplaceOperator(
            Problems.stackZWpickX,
            Plan.of(listOf(pickA, stackAB, pickC)),
            pickC,
            2,
            State.of(
                Fluents.onAB,
                Fluents.clearA,
                Fluents.atCFloor,
                Fluents.clearC,
                Fluents.atDFloor,
                Fluents.clearD,
                Fluents.atBFloor,
                Fluents.armEmpty
            ),
            pickD
        )

        val hypotheticalPlan = stripsPlanner.plan(q3.buildHypotheticalProblem().first()).first()
        val explanation = Explanation.of(q3.plan, hypotheticalPlan, q3)
        val contrastiveExplanation = Explanation.of(
            q3.plan,
            hypotheticalPlan,
            q3
        )
        explanation shouldBe contrastiveExplanation
        explanation.isPlanValid() shouldBe true
    }
}
