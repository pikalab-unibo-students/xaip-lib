import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.buildHdomain
import resources.ExplanationUtils.buildHproblem
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.newPredicate
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Actions
import resources.domain.BlockWorldDomain.Operators.stackBC
import resources.domain.BlockWorldDomain.Operators.unstackBA
import resources.domain.BlockWorldDomain.Operators.unstackCD
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems

class ExplanationQuestion2RemoveaSpecificGroundedAction : AnnotationSpec() {
    /*
    2. Why is action A used in state, rather not being used? // remove specific grounded action
    4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions
    */

    @Test
    fun testQuestion2PlanNotPossible() {
        val question = ExplanationUtils.Question1(
            unstackBA,
            Problems.stackBC,
            Plan.of(listOf(unstackBA, stackBC))
        )
        println(question)

        val newPredicate = newPredicate(question.actionToAddOrToRemove, true)
        // println("new predicate: " + newPredicate.name)

        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate) // new predicate
        // println("new fluent: $newFluent")

        val newAction = createNewAction(Actions.unstack, newFluent, true) // new action
        // println("updated action: $newAction")

        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)
        println(hDomain)
        val hProblem = buildHproblem(hDomain, question.problem, newFluent, null, true)
        println(hProblem)

        val hplan = stripsPlanner.plan(hProblem).toSet()

        buildExplanation(question.originalPlan, hplan.first(), question.actionToAddOrToRemove)
    }

    @Test
    fun testQuestion2() {
        val question = ExplanationUtils.Question1(
            unstackCD,
            Problems.stackBC,
            Plan.of(listOf(unstackCD, unstackBA, stackBC))
        )
        // println(question)

        val newPredicate = newPredicate(question.actionToAddOrToRemove, true)
        // println("new predicate: " + newPredicate.name)

        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate) // new predicate
        // println("new fluent: $newFluent")

        val newAction = createNewAction(Actions.unstack, newFluent, true) // new action
        // println("updated action: $newAction")

        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)

        val hProblem = buildHproblem(hDomain, question.problem, newFluent, null, true)
        // println(HProblem)

        val hplan = stripsPlanner.plan(hProblem).toSet()

        val explanation = buildExplanation(question.originalPlan, hplan.first(), question.actionToAddOrToRemove)

        var newActionGrounded =
            Operator.of(newAction).apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.c))
        newActionGrounded =
            newActionGrounded.apply(VariableAssignment.of(BlockWorldDomain.Values.Y, BlockWorldDomain.Values.d))

        val contrastiveExplanation = ExplanationUtils.ContrastiveExplanation(
            question.originalPlan,
            hplan.first(),
            question.actionToAddOrToRemove,
            setOf(newActionGrounded),
            setOf(question.actionToAddOrToRemove),
            setOf(unstackBA, stackBC)
        )

        explanation shouldBe contrastiveExplanation
    }
}
