
import io.kotest.core.spec.style.AnnotationSpec
import resources.domain.BlockWorldDomain

class ExplanationTest : AnnotationSpec() {
    /*
    1.“Why is action A used in the plan, rather than not being used?” //add action to a state
    2.“Why is action A not used in the plan, rather than being used?” //remove a specific grounded action
    “Why is action A used, rather than action B?”
    “Why is action A used before/after action B (rather than after/before)?”
     */
    data class Question1(val actionToAdd: Action, val problem: Problem, val plan: Plan)
    data class ContrastiveExplanation(val addList: Set<Action>, val deleteList: Set<Action>, val existingList: Set<Action>)

    fun buildExplanation(plan: Plan, Hplan: Plan) {
        val addList: MutableSet<Action> = mutableSetOf()
        val deleteList: MutableSet<Action> = mutableSetOf()
        val existingList: MutableSet<Action> = mutableSetOf()

        plan.actions.map {
            if (!Hplan.actions.contains(it)) deleteList.add(it)
            if (Hplan.actions.contains(it)) existingList.add(it)
        }

        Hplan.actions.map {
            if (!plan.actions.contains(it)) addList.add(it)
        }

        val explanation = ContrastiveExplanation(addList, deleteList, existingList)
        println(explanation)
    }

    fun comparePlans(plan: Plan, Hplan: Plan) {
        println("plan:$plan")
        println("Hplan:" + Hplan.actions.toList())

        val addList: MutableSet<Action> = mutableSetOf()
        val deleteList: MutableSet<Action> = mutableSetOf()
        val existingList: MutableSet<Action> = mutableSetOf()

        plan.actions.map {
            if (!Hplan.actions.contains(it)) deleteList.add(it)
            if (Hplan.actions.contains(it)) existingList.add(it)
        }

        Hplan.actions.map {
            if (!plan.actions.contains(it)) addList.add(it)
        }

        val explanation = ContrastiveExplanation(addList, deleteList, existingList)
        println(explanation)
    }

    @Test
    fun testQuestion1() {
        val questionAddActionPlan = Question1(
            BlockWorldDomain.Actions.unstack,
            BlockWorldDomain.Problems.stackBC,
            BlockWorldDomain.Planners.stripsPlanner.plan(BlockWorldDomain.Problems.stackBC).last()
        )

        println("Piani disponibili: " + BlockWorldDomain.Planners.stripsPlanner.plan(BlockWorldDomain.Problems.stackBC).toSet().size)
        fun newPredicated(action: Action): Predicate =
            Predicate.of("has_done_unstack_" + action.name, action.parameters.values.toList())

        val predicate = newPredicated(questionAddActionPlan.actionToAdd)
        println("predicate: " + predicate.name)

        fun newFluent(action: Action, predicate: Predicate): Fluent =
            Fluent.positive(predicate, BlockWorldDomain.Values.a, BlockWorldDomain.Values.b) // TODO(fixa sta roba con gli argomenti delle azioni)

        val fluent = newFluent(questionAddActionPlan.actionToAdd, predicate)
        println("fluent: $fluent")

        fun newAction(action: Action, fluent: Fluent) =
            Action.of(
                name = action.name + "'",
                parameters = action.parameters,
                preconditions = action.preconditions,
                effects = mutableSetOf(Effect.of(fluent)).also { it.addAll(action.effects) }
            )

        val action = newAction(BlockWorldDomain.Actions.unstack, fluent)
        println("action: $action")

        val HDomain = Domain.of(
            name = questionAddActionPlan.problem.domain.name,
            predicates = mutableSetOf(predicate).also { it.addAll(questionAddActionPlan.problem.domain.predicates) },
            actions = mutableSetOf(action).also {
                questionAddActionPlan.problem.domain.actions.map { oldAction ->
                    if (oldAction.name != action.name.filter { char -> char.isLetter() }) it.add(oldAction)
                }
            },
            types = questionAddActionPlan.problem.domain.types
        )

        println("HDomain action:" + HDomain.actions.toList())

        val HProblem = Problem.of(
            domain = HDomain,
            objects = questionAddActionPlan.problem.objects,
            initialState = questionAddActionPlan.problem.initialState,
            goal = FluentBasedGoal.of(mutableSetOf(fluent).also { it.addAll((questionAddActionPlan.problem.goal as FluentBasedGoal).targets) })
        )

        val plan = questionAddActionPlan.plan
        val Hplan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).first()

        println("plan:" + plan.actions.toList())
        println("Hplan:" + Hplan.actions.toList())

        buildExplanation(plan, Hplan)
        comparePlans(plan, Hplan)
    }

    @Test
    fun testQuestion2() {
        val questionAddActionPlan = Question1(
            BlockWorldDomain.Actions.unstack,
            BlockWorldDomain.Problems.stackBC,
            Plan.of(emptyList()) // TODO("mettici il piano del test precedente")
        )

        fun newPredicated(action: Action): Predicate =
            Predicate.of("not_has_done_unstack_" + action.name, action.parameters.values.toList())

        val predicate = newPredicated(questionAddActionPlan.actionToAdd)
        println("predicate: " + predicate.name)

        fun newFluent(action: Action, predicate: Predicate): Fluent =
            Fluent.positive(predicate, BlockWorldDomain.Values.a, BlockWorldDomain.Values.b) // TODO(fixa sta roba con gli argomenti delle azioni)

        val fluent = newFluent(questionAddActionPlan.actionToAdd, predicate)
        println("fluent: $fluent")

        fun newAction(action: Action, fluent: Fluent) =
            Action.of(
                name = action.name + "'",
                parameters = action.parameters,
                preconditions = action.preconditions,
                effects = mutableSetOf(Effect.negative(fluent)).also { it.addAll(action.effects) }
            )

        val action = newAction(BlockWorldDomain.Actions.unstack, fluent)
        println("action: $action")

        val HDomain = Domain.of(
            name = questionAddActionPlan.problem.domain.name,
            predicates = mutableSetOf(predicate).also { it.addAll(questionAddActionPlan.problem.domain.predicates) },
            actions = mutableSetOf(action).also {
                questionAddActionPlan.problem.domain.actions.map { oldAction ->
                    if (oldAction.name != action.name.filter { char -> char.isLetter() }) it.add(oldAction)
                }
            },
            types = questionAddActionPlan.problem.domain.types
        )

        val HProblem = Problem.of(
            domain = HDomain,
            objects = questionAddActionPlan.problem.objects,
            initialState = State.of(mutableSetOf(fluent).also { it.addAll(questionAddActionPlan.problem.initialState.fluents) }),
            goal = FluentBasedGoal.of(mutableSetOf(fluent).also { it.addAll((questionAddActionPlan.problem.goal as FluentBasedGoal).targets) })
        )

        val plan = questionAddActionPlan.plan
        val Hplan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).first()

        println("plan:" + plan.actions.toList())
        println("Hplan:" + Hplan.actions.toList())

        buildExplanation(plan, Hplan)
        comparePlans(plan, Hplan)
    }
}
