import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.newPredicate
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.stackBC
import resources.domain.BlockWorldDomain.Operators.unstackBA

class ExplanationQuestion2RemoveaSpecificGroundedAction : AnnotationSpec() {
    /*
    2. Why is action A used in state , rather not being used? // remove specific grounded action
    4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions
    */

    @Test
    fun testQuestion2() {
        val question = ExplanationUtils.Question1(
            unstackBA,
            BlockWorldDomain.Problems.stackBC,
            Plan.of(listOf(unstackBA, stackBC))
        )
        println(question)

        val newPredicate = newPredicate(question.actionToAddOrToRemove, true)
        // println("new predicate: " + newPredicate.name)

        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate) // new predicate
        // println("new fluent: $newFluent")

        val newAction = createNewAction(BlockWorldDomain.Actions.unstack, newFluent, true) // new action
        // println("updated action: $newAction")

        val hDomain = Domain.of( // domain extended
            name = question.problem.domain.name,
            predicates = mutableSetOf(newPredicate).also { it.addAll(question.problem.domain.predicates) },
            actions = mutableSetOf(newAction).also {
                question.problem.domain.actions.map { oldAction ->
                    if (oldAction.name != newAction.name.filter { char ->
                        char.isLetter()
                    }
                    ) it.add(oldAction)
                }
            },
            types = question.problem.domain.types
        )

        val hProblem = Problem.of( // problem extended
            domain = hDomain,
            objects = question.problem.objects,
            initialState = State.of(
                mutableSetOf(newFluent).also { it.addAll(question.problem.initialState.fluents) }
            ), // extended
            goal = FluentBasedGoal.of(
                mutableSetOf(newFluent).also {
                    it.addAll((question.problem.goal as FluentBasedGoal).targets)
                }
            )
            // extended
        )

        val hplan = BlockWorldDomain.Planners.stripsPlanner.plan(hProblem).toSet()

        buildExplanation(question.originalPlan, hplan.first(), question.actionToAddOrToRemove)
    }
}
