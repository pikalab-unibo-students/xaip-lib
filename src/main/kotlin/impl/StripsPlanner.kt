package impl

import Action
import Applicable
import Effect
import Fluent
import FluentBasedGoal
import Plan
import Planner
import Problem
import State
import VariableAssignment
import java.util.*

internal class StripsPlanner : Planner {

    override fun plan(problem: Problem): Sequence<Plan> = sequence {
        if (problem.domain.axioms.isNotEmpty()) error("Axioms are not yet supported")
        var i = 1
        var goOn = true
        var set = emptySet<Plan>()
        while (goOn) {
            for (p in plan(problem.initialState, problem.domain.actions, problem.goal as FluentBasedGoal, i++)) {
                yield(p)
                if (set.size != set.plus(p).size) {
                    set = set.plus(p)
                    goOn = false
                }
            }
        }
    }

    private fun Stack<Applicable<*>>.apply(substitution: VariableAssignment) {
        val i = listIterator()
        while (i.hasNext()) {
            val x = i.next()
            i.set(x.apply(substitution))
        }
    }

    private data class ChoicePoint(val stack: Stack<Applicable<*>>, val state: State, val plan: MutableList<Action>)

    private fun updateStack(stack: Stack<Applicable<*>>, elem: Any, h: Any? = null): Stack<Applicable<*>> {
        var stackCopy = Stack<Applicable<*>>().also { it.addAll(stack) }
        when (elem) {
            is VariableAssignment -> {
                stackCopy.apply(elem)
            }
            is Action -> {
                stackCopy.push(elem)
                stackCopy.addAll(elem.preconditions)
            }
            is Effect -> {
                stackCopy.apply((h as Effect).mostGeneralUnifier(elem))
            }
        }
        return stackCopy
    }

    private fun updateChoicePoints(
        list: List<Any>,
        stack: Stack<Applicable<*>>,
        choicePoints: Deque<ChoicePoint>,
        currentState: State,
        plan: List<Action>,
        h: Any? = null
    ): Deque<ChoicePoint> {
        for (elem in list.subList(1, list.size)) {
            @Suppress("UNCHECKED_CAST")
            var stackCopy = stack.clone() as Stack<Applicable<*>>
            if (elem is VariableAssignment) {
                stackCopy.apply(elem )
            } else if (elem is Action) {
                stackCopy.push(elem)
                stackCopy.addAll((elem).preconditions)
            } else if (elem is Effect) {
                stackCopy.apply((h as Effect).mostGeneralUnifier(elem))
            }
            choicePoints.add(
                ChoicePoint(
                    stackCopy,
                    currentState,
                    plan.toMutableList()
                )
            )
        }
        return choicePoints
    }

    @Suppress("UNCHECKED_CAST")
    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal,
        maxDepth: Int
    ): Sequence<Plan> = sequence<Plan> {
        var currentState = initialState
        var stack = Stack<Applicable<*>>().also { it.push(goal) }
        var choicePoints: Deque<ChoicePoint> = LinkedList()
        var plan = mutableListOf<Action>()

        fun backtrackOrFail(): Boolean {
            if (!choicePoints.isEmpty()) {
                val choicePoint = choicePoints.pollFirst()
                stack = choicePoint.stack
                currentState = choicePoint.state
                plan = choicePoint.plan
                return false
            }
            return true
        }

        while (true) {
            while (stack.isNotEmpty()) {
                val head = stack.peek()
                stack.pop()
                when {
                    head is Fluent -> { // "applica la sostituzione a tutto lo stack"
                        if (currentState.fluents.any { it.match(head) }) {
                            val substitutions =
                                currentState.fluents.filter { it.match(head) }.map { it.mostGeneralUnifier(head) }
/*
                            for (s in substitutions.subList(1, substitutions.size)) {
                                val stackCopy: Stack<Applicable<*>> = stack.clone() as Stack<Applicable<*>>
                                stackCopy.apply(s)
                                choicePoints.add(ChoicePoint(stackCopy, currentState, plan.toMutableList()))
                            }
                            */
                            choicePoints = updateChoicePoints(substitutions, stack, choicePoints, currentState, plan)
                            stack = updateStack(stack, substitutions.first())
                        } else { // "retrieve dell'azione - push dell'azione -push delle precondizioni dell'azione"
                            val h = Effect.of(head)
                            val actionsMatched = actions.map { it.refresh() }
                                .filter { action -> action.positiveEffects.any { effect -> effect.match(h) } }
                            val action = actionsMatched.first()
                            choicePoints = updateChoicePoints(actionsMatched, stack, choicePoints, currentState, plan)
                            stack = updateStack(stack, action, head)

                            val effectsMatched = action.positiveEffects.filter { effect -> effect.match(h) }
                            choicePoints = updateChoicePoints(effectsMatched, stack, choicePoints, currentState, plan, h)
                            stack = updateStack(stack, effectsMatched.first(), h)
                        }
                    }
                    (head is FluentBasedGoal) -> {
                        stack.addAll(head.targets)
                    }
                    (head is Action) -> { // applicare l'azione a currentState e aggiornarlo"
                        val states = currentState.apply(head).toList()
                        if (states.isNotEmpty()) {
                            currentState = states.first()
                            updateChoicePoints(states, stack, choicePoints, currentState, plan)
                            plan.add(head)
                        }
                        if ((plan.size > maxDepth || states.isEmpty()) && (backtrackOrFail())) return@sequence
                    }
                    else -> {
                        error("Handle the case where $head is ${head::class}(probably via backtracking)")
                    }
                }
            }
            yield(Plan.of(plan))
            if (backtrackOrFail()) return@sequence
        }
    }.distinct()
}
