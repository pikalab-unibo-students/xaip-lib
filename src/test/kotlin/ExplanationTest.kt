
import resources.domain.BlockWorldDomain

class ExplanationTest {
    /*
    “Why is action A used in the plan, rather than not being used?” //add action to a state
    “Why is action A not used in the plan, rather than being used?”
    “Why is action A used, rather than action B?”
    “Why is action A used before/after action B (rather than after/before)?”
     */
    data class Question1(val action: Action, val problem: Problem, val plan: Plan)
    data class ContrastiveExplanation(val addList: Set<Action>, val deleteList: Set<Action>, val existingList: Set<Action>)
    fun Action.getParameters() = this.parameters
    fun test() {
        val stackBC = Problem.of(
            domain = BlockWorldDomain.Domains.blockWorld,
            objects = BlockWorldDomain.ObjectSets.all,
            initialState = BlockWorldDomain.States.initial,
            goal = BlockWorldDomain.Goals.onBC
        )
        val question: Question1 = Question1(
            BlockWorldDomain.Actions.unstack,
            BlockWorldDomain.Problems.stackBC,
            BlockWorldDomain.Planners.stripsPlanner.plan(BlockWorldDomain.Problems.stackBC).first()
        )

        fun newPredicated(action: Action): Predicate =
            Predicate.of("has_done" + action.name) // per me non ci vogliono tipi

        val predicate = newPredicated(question.action)
        fun newFluent(action: Action): Fluent =
            Fluent.positive(predicate, *action.parameters.keys.toTypedArray())

        val fluent = newFluent(question.action)
        val fluents = ((question.problem.goal as FluentBasedGoal).targets).toMutableSet()
        fluents.add(fluent)
        val HDomain = Domain.of(
            name = question.problem.domain.name,
            predicates = mutableSetOf(predicate).also { it.addAll(question.problem.domain.predicates) },
            actions = question.problem.domain.actions,
            types = question.problem.domain.types
        )
        val HProblem = Problem.of(
            domain = HDomain,
            objects = question.problem.objects,
            initialState = question.problem.initialState,
            goal = FluentBasedGoal.of(fluents)
        )
        val plan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).first()
        // val explanation: ContrastiveExplanation = TODO()
        val addList: MutableSet<Action> = mutableSetOf()
        val deleteList: MutableSet<Action> = mutableSetOf()
        val existingList: MutableSet<Action> = mutableSetOf()
        question.plan.actions.map {
            if (!plan.actions.contains(it)) addList.add(it)
            if (plan.actions.contains(it)) existingList.add(it)
        }
        plan.actions.map {
            if (!question.plan.actions.contains(it)) deleteList.add(it)
        }
        val explanation = ContrastiveExplanation(addList, deleteList, existingList)
        println(explanation)
    }
}
