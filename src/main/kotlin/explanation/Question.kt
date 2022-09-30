package explanation

import Action
import Domain
import Fluent
import FluentBasedGoal
import Operator
import Plan
import Predicate
import Problem
import State

interface Question {
    val problem: Problem
    val plan: Plan
    val focus: Operator

    fun buildHdomain(): Domain
    fun buildHproblem(): Problem
}

open class AbstractQuestion() {
    // 1.
    open lateinit var newPredicate: Predicate
    open lateinit var newGroundFluent: Fluent
    open lateinit var newFluent: Fluent

    // 2.
    open lateinit var oldAction: Action

    // 3.
    open lateinit var newAction: Action

    // 4.
    open lateinit var hDomain: Domain

    // 5.
    open lateinit var hProblem: Problem
    fun reorderPlan(
        plan: Plan,
        actionsToPosticipate: List<Operator>,
        actionsToAnticipate: List<Operator>
    ): List<Operator> {
        val indiceInizio = plan.actions.indexOf(actionsToPosticipate.first())
        val indiceFine = plan.actions.indexOf(actionsToPosticipate.last())
        val indiceInizio2 = plan.actions.indexOf(actionsToAnticipate.last())
        val indiceFine2 = plan.actions.indexOf(actionsToAnticipate.last())

        val reorderedPlan = plan.actions.subList(0, indiceInizio).toMutableList()
            .also { it.addAll(actionsToAnticipate) }
            .also {
                it.addAll(
                    plan.actions.subList(
                        indiceFine + 1,
                        indiceInizio2
                    )
                )
            }
            .also { it.addAll(actionsToPosticipate) }
            .also {
                it.addAll(
                    plan.actions.subList(
                        indiceFine2 + 1,
                        plan.actions.size
                    )
                )
            }
        return reorderedPlan
    }
    fun findAction(inputOperator: Operator, actionList: Iterable<Action>): Action =
        actionList.first { it.name == inputOperator.name }
    fun createNewGroundFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.args.toTypedArray())

    fun createNewFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.parameters.keys.toTypedArray())

    fun createNewPredicate(action: Action, name: String, negated: Boolean = false): Predicate =
        if (negated) Predicate.of(name + action.name, action.parameters.values.toList())
        else Predicate.of(name + action.name, action.parameters.values.toList())

    fun createNewAction(action: Action, fluent: Fluent, negated: Boolean = false): Action {
        return Action.of(
            name = action.name + "^",
            parameters = action.parameters,
            preconditions = action.preconditions,
            effects = if (negated) mutableSetOf(Effect.negative(fluent)).also {
                it.addAll(action.effects)
            } else mutableSetOf(Effect.of(fluent)).also { it.addAll(action.effects) }
        )
    }
    fun buildHdomain(domain: Domain, newPredicate: Predicate, newAction: Action) =
        Domain.of( // domain extended
            name = domain.name,
            predicates = mutableSetOf(newPredicate).also { it.addAll(domain.predicates) },
            actions = mutableSetOf(newAction).also {
                domain.actions.map { oldAction ->
                    if (oldAction.name != newAction.name.filter { char ->
                        char.isLetter()
                    } // se il nome è diverso lo aggiungo
                    ) it.add(oldAction)
                }
            },
            types = domain.types
        )

    fun buildHproblem(
        hDomain: Domain,
        problem: Problem,
        newFluent: Fluent?,
        state: State?,
        updateState: Boolean = false
    ) =
        Problem.of( // problem extended
            domain = hDomain,
            objects = problem.objects,
            initialState =
            if (updateState) State.of(
                mutableSetOf(newFluent!!).also {
                    it.addAll(problem.initialState.fluents)
                }
            ) else state ?: problem.initialState, // extended
            goal = if (newFluent != null) {
                FluentBasedGoal.of(
                    (problem.goal as FluentBasedGoal).targets.toMutableSet().also {
                        it.add(newFluent)
                    }
                )
            } else problem.goal
        )
}
class Question1(override val problem: Problem, override val plan: Plan, override val focus: Operator) : Question, AbstractQuestion() {
    // 1.
    override var newPredicate = createNewPredicate(focus, "has_done_")

    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)
    override var newFluent = createNewFluent(focus, newPredicate)

    // 2.
    override var oldAction =
        findAction(focus, problem.domain.actions)

    // 3.
    override var newAction = createNewAction(oldAction, newFluent)

    // 4.
    override var hDomain = buildHdomain()

    override fun buildHdomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHproblem(): Problem = buildHproblem(hDomain, problem, newGroundFluent, null)
}

class Question2(override val problem: Problem, override val plan: Plan, override val focus: Operator) : Question, AbstractQuestion() {
    override var newPredicate = createNewPredicate(focus, "not_done_", true)
    override var newFluent = createNewFluent(focus, newPredicate)
    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)

    override var oldAction =
        findAction(focus, problem.domain.actions)

    override var newAction = createNewAction(oldAction, newFluent, true)

    override var hDomain = buildHdomain()

    override fun buildHdomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHproblem(): Problem = buildHproblem(hDomain, problem, newGroundFluent, null, true)
}

class Question3(override val problem: Problem, override val plan: Plan, override val focus: Operator, inState: State? = null) : Question, AbstractQuestion() {
    private val newProblem = if (inState != null) {
        Problem.of(
            domain = problem.domain,
            objects = problem.objects,
            initialState = inState,
            goal = problem.goal
        )
    } else {
        problem
    }

    // A. TODO( estendi a considerare tutti gli stati possibili)
    private val newState = newProblem.initialState.apply(focus).first()
    override var hDomain = buildHdomain()

    override fun buildHdomain(): Domain = Domain.of(
        name = newProblem.domain.name,
        predicates = newProblem.domain.predicates,
        actions = newProblem.domain.actions,
        types = newProblem.domain.types
    )

    override fun buildHproblem(): Problem = Problem.of(
        domain = hDomain,
        objects = newProblem.objects,
        initialState = newState,
        goal = newProblem.goal
    )
}

class Question4(override val problem: Problem, override val plan: Plan, override val focus: Operator, val alternativePlan: Plan) : Question, AbstractQuestion() {

    )
    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}

class Question5(override val problem: Problem, override val plan: Plan, override val focus: Operator, val alternativePlan: Plan) : Question {
    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}
