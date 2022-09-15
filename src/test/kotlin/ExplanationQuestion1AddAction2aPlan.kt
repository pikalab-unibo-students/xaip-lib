import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.ContrastiveExplanation
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.buildHdomain
import resources.ExplanationUtils.buildHproblem
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.findAction
import resources.ExplanationUtils.newPredicate
import resources.domain.BlockWorldDomain.Operators.stackCB
import resources.domain.BlockWorldDomain.Operators.unstackCD
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class `ExplanationQuestion1AddAction2aPlan` : AnnotationSpec() {
    // 1.“Why is action A not used in the plan, rather than being used?” //add action to a state
    @Test
    fun testQuestion1() {
        val plans = stripsPlanner.plan(Problems.stackCB).toSet()
        val questionAddCD = ExplanationUtils.Question1(
            unstackCD,
            Problems.stackCB,
            plans.first()
        )
        val newPredicate = newPredicate(questionAddCD.actionToAddOrToRemove)
        val newFluent = createNewFluent(questionAddCD.actionToAddOrToRemove, newPredicate)
        val notGroundAction =
            findAction(questionAddCD.actionToAddOrToRemove, questionAddCD.problem.domain.actions)
        val newAction = createNewAction(notGroundAction, newFluent)
        val hDomain = buildHdomain(questionAddCD.problem.domain, newPredicate, newAction)
        val hProblem = buildHproblem(hDomain, questionAddCD.problem, newFluent, null)
        val plan = questionAddCD.originalPlan
        val hplan = stripsPlanner.plan(hProblem).first()
        val explanation: ContrastiveExplanation =
            buildExplanation(plan, hplan, questionAddCD.actionToAddOrToRemove)
        var newActionGrounded = Operator.of(newAction).apply(VariableAssignment.of(Values.X, Values.c))
        newActionGrounded = newActionGrounded.apply(VariableAssignment.of(Values.Y, Values.d))
        val contrastiveExplanation = ContrastiveExplanation(
            questionAddCD.originalPlan,
            hplan,
            questionAddCD.actionToAddOrToRemove,
            setOf(newActionGrounded),
            setOf(questionAddCD.actionToAddOrToRemove),
            setOf(stackCB)
        )

        explanation shouldBe contrastiveExplanation
    }
}
