import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.newPredicate
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.stackCB
import resources.domain.BlockWorldDomain.Operators.unstackBA

class `ExplanationQuestion2-RemoveaSpecificGroundedAction` : AnnotationSpec() {
    /*
    3.“Why is action A used, rather than action B?” // replacing action in a state
    4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions
     */

    @Test
    fun testQuestion2() {
        val questionAddActionPlan = ExplanationUtils.Question1(
            unstackBA,
            BlockWorldDomain.Problems.stackBC,
            Plan.of(listOf(unstackBA, stackCB))
        )

        val newPredicate = newPredicate(questionAddActionPlan.actionToAdd, true)
        println("new predicate: " + newPredicate.name)

        val newFluent = createNewFluent(questionAddActionPlan.actionToAdd, newPredicate) // new predicate
        println("new fluent: $newFluent")

        val newAction = createNewAction(BlockWorldDomain.Actions.unstack, newFluent, true) // new action
        println("updated action: $newAction")

        val HDomain = Domain.of( // domain extended
            name = questionAddActionPlan.problem.domain.name,
            predicates = mutableSetOf(newPredicate).also { it.addAll(questionAddActionPlan.problem.domain.predicates) },
            actions = mutableSetOf(newAction).also {
                questionAddActionPlan.problem.domain.actions.map { oldAction ->
                    if (oldAction.name != newAction.name.filter { char ->
                        char.isLetter()
                    }
                    ) it.add(oldAction)
                }
            },
            types = questionAddActionPlan.problem.domain.types
        )

        val HProblem = Problem.of( // problem extended
            domain = HDomain,
            objects = questionAddActionPlan.problem.objects,
            initialState = State.of(
                mutableSetOf(newFluent).also { it.addAll(questionAddActionPlan.problem.initialState.fluents) }
            ), // extended
            goal = questionAddActionPlan.problem.goal/*FluentBasedGoal.of(
                mutableSetOf(newFluent).also {
                    it.addAll((questionAddActionPlan.problem.goal as FluentBasedGoal).targets)
                }
                */
            // extended
        )

        val plan = questionAddActionPlan.plan
        val Hplan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).toSet()

        println("plan:" + plan.actions.toList())
        println("Hplan:" + Hplan)

        buildExplanation(plan, Hplan.first())
    }
}
