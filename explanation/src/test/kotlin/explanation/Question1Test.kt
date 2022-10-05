package explanation

import explanation.impl.Question1
import explanation.oldVersion.ExplanationUtils
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.Operators
import resources.domain.BlockWorldDomain.Planners
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class Question1Test : AnnotationSpec() {
    @Test
    fun `Use pickA instead of pick B in armNotEmpty problem`() {
        val q1 = Question1(
            Problems.armNotEmpty,
            Plan.of(listOf(Operators.pickB)),
            Operators.pickA,
            0
        )

        val newPredicate = q1.createNewPredicate(q1.focus, "has_done_")
        val newFluent = q1.createNewFluent(q1.focus, newPredicate)
        val oldAction =
            q1.findAction(q1.focus, q1.problem.domain.actions)
        val newAction = ExplanationUtils.createNewAction(oldAction, newFluent)
        val newGroundAction = Operator.of(newAction).apply(VariableAssignment.of(Values.X, Values.a))

        val hPlan = Planners.stripsPlanner.plan(q1.buildHproblem()).first()
        val explanation = Explanation.of(q1.plan, hPlan, q1)

        val contrastiveExplanation = Explanation.of(
            q1.plan,
            Plan.of(listOf(newGroundAction)),
            q1
        )
        explanation shouldBe contrastiveExplanation
    }
}
