package explanation

import Operator
import Plan
import VariableAssignment
import explanation.impl.Question2
import explanation.oldVersion.ExplanationUtils
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.Operators.pickA
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

        val newPredicate = q2.createNewPredicate(q2.focus, "not_done_", true)
        val newFluent = q2.createNewFluent(q2.focus, newPredicate)
        val oldAction =
            q2.findAction(q2.focus, q2.problem.domain.actions)
        val newAction = ExplanationUtils.createNewAction(oldAction, newFluent, true)
        val newGroundAction = Operator.of(newAction).apply(VariableAssignment.of(Values.X, Values.b))

        val explanation = Explanation.of(q2.plan, hplan)

        val contrastiveExplanation = Explanation.of(
            q2.plan,
            Plan.of(listOf(newGroundAction))
        )
        explanation shouldBe contrastiveExplanation
    }
}
