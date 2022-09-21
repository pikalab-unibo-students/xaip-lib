import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils.ContrastiveExplanation
import resources.ExplanationUtils.Question1
import resources.ExplanationUtils.buildHdomain
import resources.ExplanationUtils.buildHproblem
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewGroundFluent
import resources.ExplanationUtils.createNewPredicate
import resources.domain.BlockWorldDomain.Actions
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class ExplanationQuestion2RemoveaSpecificGroundedAction : AnnotationSpec() {
    // 2. Why is action A used in state, rather not being used? // remove specific grounded action

    @Test
    fun testQuestion2() {
        val question = Question1(
            pickA,
            Problems.armNotEmpty,
            Plan.of(listOf(pickA))
        )
        println(question)

        val newPredicate = createNewPredicate(question.actionToAddOrToRemove, true)
        println("new predicate: $newPredicate")

        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate)
        println("new fluent: $newFluent")
        val newGroundFluent = createNewGroundFluent(question.actionToAddOrToRemove, newPredicate)
        println("new ground fluent: $newFluent")

        val newAction = createNewAction(Actions.pick, newFluent, true) // new action
        println("updated action: $newAction")
        val newGroundAction = Operator.of(newAction).apply(VariableAssignment.of(Values.X, Values.b))

        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)
        val hProblem = buildHproblem(hDomain, question.problem, newGroundFluent, null, true)
        println("Hproblem $hProblem")

        val hplan = stripsPlanner.plan(hProblem).first()

        val explanation = ContrastiveExplanation.of(question.originalPlan, hplan, question.actionToAddOrToRemove)

        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            Plan.of(listOf(newGroundAction)),
            question.actionToAddOrToRemove
        )

        explanation shouldBe contrastiveExplanation
    }
}
