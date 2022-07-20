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
        var i = 1
        var goOn = true
        var set= emptySet<List<Action>>()
        while (goOn) {
            goOn = true
            for (p in plan(problem.initialState, problem.domain.actions, problem.goal as FluentBasedGoal, i++)) {
                yield(p)
                set.plus(p)
                if(set.size == set.plus(p).size) goOn = false
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

    @Suppress("UNCHECKED_CAST")
    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal,
        maxDepth: Int
    ): Sequence<Plan> = sequence<Plan> {
        var currentState = initialState
        var stack = Stack<Applicable<*>>().also { it.push(goal) }
        val choicePoints: Deque<ChoicePoint> = LinkedList()
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
                when {
                    head is Fluent -> {//"applica la sostituzione a tutto lo stack"
                        if (currentState.fluents.any { it.match(head) }) {
                            val substitutions =
                                currentState.fluents.filter { it.match(head) }.map { it.mostGeneralUnifier(head) }
                            stack.pop()
                            val substitution = substitutions.first()
                            for (s in substitutions.subList(
                                1,
                                substitutions.size
                            )) {//sostituzioni possibile= variabile con a,b,c
                                val stackCopy: Stack<Applicable<*>> = stack.clone() as Stack<Applicable<*>>
                                stackCopy.apply(s)
                                choicePoints.add(
                                    ChoicePoint(
                                        stackCopy,
                                        currentState,
                                        plan.toMutableList()
                                    )
                                )
                            }
                            stack.apply(substitution)
                        } else {//"retrieve dell'azione - push dell'azione -push delle precondizioni dell'azione"
                            val h = Effect.of(head)
                            stack.pop()

                            val actionsMatched = actions.map { it.refresh() }
                                .filter { action -> action.positiveEffects.any { effect -> effect.match(h) } }

                            val action = actionsMatched.first()
                            for (elem in actionsMatched.subList(1, actionsMatched.size)) {
                                val stackCopy: Stack<Applicable<*>> = stack.clone() as Stack<Applicable<*>>
                                stackCopy.push(elem)
                                stackCopy.addAll(elem.preconditions)
                                choicePoints.add(
                                    ChoicePoint(
                                        stackCopy,
                                        currentState,
                                        plan.toMutableList()
                                    )
                                )
                            }

                            stack.push(action)
                            stack.addAll(action.preconditions)

                            val effectsMatched = action.positiveEffects.filter { effect -> effect.match(h) }
                            val effect = effectsMatched.first()
                            for (elem in effectsMatched.subList(1, effectsMatched.size)) {
                                val stackCopy: Stack<Applicable<*>> = stack.clone() as Stack<Applicable<*>>
                                stackCopy.apply(h.mostGeneralUnifier(elem))
                                choicePoints.add(
                                    ChoicePoint(
                                        stackCopy,
                                        currentState,
                                        plan.toMutableList()
                                    )
                                )
                            }
                            stack.apply(h.mostGeneralUnifier(effect))
                        }
                    }
                    (head is FluentBasedGoal) -> {
                        stack.pop()
                        for (fluent in head.targets)
                            stack.push(fluent)
                    }
                    (head is Action) -> {//applicare l'azione a currentState e aggiornarlo"
                        stack.pop()
                        val states = currentState.apply(head).toList()
                        if (states.isEmpty()) {
                            if (backtrackOrFail()) return@sequence
                        } else {
                            currentState = states.first()
                            for (state in states.subList(1, states.size)) {
                                choicePoints.add(ChoicePoint(stack, state, plan))
                            }
                            plan.add(head)
                            if (plan.size > maxDepth) {
                                if (backtrackOrFail()) return@sequence
                            }
                        }
                    }
                    else -> {
                        TODO("Handle the case where $head is ${head::class} (probably via backtracking)")
                    }
                }
            }

            yield(Plan.of(plan))

            if (backtrackOrFail()) return@sequence
        }
    }.distinct()
}
