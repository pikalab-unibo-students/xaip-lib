import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.newPredicated
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.stackCB
import resources.domain.BlockWorldDomain.Operators.unstackBA

class `ExplanationQuestion2-RemoveaSpecificGroundedAction` : AnnotationSpec() {
    /*
    1.“Why is action A not used in the plan, rather than being used?” //add action to a state
    2.“Why is action A used in the plan, rather than not being used?” //remove a specific grounded action
    3. “Why is action A used, rather than action B?” // replacing action in a state
    “Why is action A used before/after action B (rather than after/before)?”
     */

    @Test
    fun testQuestion2() {
        val questionAddActionPlan = ExplanationUtils.Question1(
            unstackBA,
            BlockWorldDomain.Problems.stackBC,
            Plan.of(listOf(unstackBA, stackCB))
        )
        /*
        fun createNewPredicated(action: Action): Predicate =
            Predicate.of("not_has_done_" + action.name, action.parameters.values.toList())
         */
        val newPredicate = newPredicated(questionAddActionPlan.actionToAdd)
        println("new predicate: " + newPredicate.name)

        /*
        fun createNewFluent(action: Action, predicate: Predicate): Fluent = // new predicate
            Fluent.positive(predicate, BlockWorldDomain.Values.a, BlockWorldDomain.Values.b)
            // TODO(fixa sta roba con gli argomenti delle azioni)
         */
        val newFluent = createNewFluent(questionAddActionPlan.actionToAdd, newPredicate) // new predicate
        println("new fluent: $newFluent")

        fun createNewAction2(action: Action, fluent: Fluent) = // extend action
            Action.of(
                name = action.name + "'",
                parameters = action.parameters,
                preconditions = action.preconditions,
                effects = mutableSetOf(Effect.positive(fluent)).also { it.addAll(action.effects) }
                // add new predicate as negative effect
            )

        val newAction = createNewAction2(BlockWorldDomain.Actions.unstack, newFluent) // new action
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
