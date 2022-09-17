import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.buildHdomain
import resources.ExplanationUtils.buildHproblem
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewPredicate
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Actions
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems

class ExplanationQuestion2RemoveaSpecificGroundedAction : AnnotationSpec() {
    /*
    2. Why is action A used in state, rather not being used? // remove specific grounded action
    4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions
    */

    @Test
    fun testQuestion2() {
        val question = ExplanationUtils.Question1(
            pickA,
            Problems.armNotEmpty,
            Plan.of(listOf(pickA))
        )
        println(question)

        val newPredicate = createNewPredicate(question.actionToAddOrToRemove, true)
        println("new predicate: $newPredicate")

        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate)
        println("new fluent: $newFluent")

        val newAction = createNewAction(Actions.pick, newFluent, true) // new action
        println("updated action: $newAction")

        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)
        val hProblem = buildHproblem(hDomain, question.problem, newFluent, null, true)
        println(hProblem)

        val hplan = stripsPlanner.plan(hProblem).first()

        buildExplanation(question.originalPlan, hplan, question.actionToAddOrToRemove)
    }

}
