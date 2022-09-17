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
import resources.ExplanationUtils.createNewPredicate
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class `ExplanationQuestion1AddAction2aPlan` : AnnotationSpec() {
    // 1.“Why is action A not used in the plan, rather than being used?” //add action to a state
    @Test
    fun testQuestion1() {
        val question = ExplanationUtils.Question1(
            pickA,
            Problems.armNotEmpty,
            Plan.of(listOf(pickB))
        )
        val newPredicate = createNewPredicate(question.actionToAddOrToRemove)
        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate)
        val notGroundAction =
            findAction(question.actionToAddOrToRemove, question.problem.domain.actions)
        val newAction = createNewAction(notGroundAction, newFluent)
        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)
        val hProblem = buildHproblem(hDomain, question.problem, newFluent, null)
        val plan = question.originalPlan
        val hplan = stripsPlanner.plan(hProblem).first()
        val explanation: ContrastiveExplanation =
            buildExplanation(plan, hplan, question.actionToAddOrToRemove)
        var newActionGrounded = Operator.of(newAction).apply(VariableAssignment.of(Values.X, Values.a))

        val contrastiveExplanation = ContrastiveExplanation(
            question.originalPlan,
            hplan,
            question.actionToAddOrToRemove,
            setOf(newActionGrounded),
            setOf(pickB),
            setOf()
        )

        explanation shouldBe contrastiveExplanation
    }

    @Test
    fun testQuestion2() {
        val question = ExplanationUtils.Question1(
            pickC,
            Problems.armNotEmpty,
            Plan.of(listOf(pickB))
        )
        val newPredicate = createNewPredicate(question.actionToAddOrToRemove)
        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate)
        val notGroundAction =
            findAction(question.actionToAddOrToRemove, question.problem.domain.actions)
        val newAction = createNewAction(notGroundAction, newFluent)
        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)
        val hProblem = buildHproblem(hDomain, question.problem, newFluent, null)
        val plan = question.originalPlan
        var newActionGrounded = Operator.of(newAction).apply(VariableAssignment.of(Values.X, Values.c))
        val hplan = stripsPlanner.plan(hProblem).filter { it.actions.contains(newActionGrounded) }.first()
        val explanation: ContrastiveExplanation =
            buildExplanation(plan, hplan, question.actionToAddOrToRemove)

        val contrastiveExplanation = ContrastiveExplanation(
            question.originalPlan,
            hplan,
            question.actionToAddOrToRemove,
            setOf(newActionGrounded),
            setOf(pickB),
            setOf()
        )

        explanation shouldBe contrastiveExplanation
    }
}
