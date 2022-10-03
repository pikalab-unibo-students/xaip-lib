package explanation.impl

import FluentBasedGoal
import Goal
import NotUnifiableException
import Operator
import State
import it.unibo.tuprolog.core.Substitution

interface Simulator {
    fun simulate(actions: List<Operator>, state: State, goal: Goal): Boolean
}

/**
 *
 */
class SimulatorImpl() : Simulator {
    class Context(val actions: List<Operator>, val state: State)

    var contextList: MutableList<Context> = mutableListOf()

    fun finalStateComplaintWithGoal(goal: FluentBasedGoal, currentState: State): Boolean {
        var indice = 0
        for (fluent in goal.targets) {
            if (!fluent.isGround) {
                for (fluentState in currentState.fluents) {
                    val tmp = try {
                        fluentState.mostGeneralUnifier(fluent)
                    } catch (_: NotUnifiableException) { null }
                    if (tmp != Substitution.empty() || tmp != Substitution.empty()) {
                        indice++
                        break
                    }
                }
            } else {
                if (currentState.fluents.contains(fluent)) indice++ else indice--
            }
        }
        return goal.targets.size == indice
    }

    override fun simulate(actions: List<Operator>, state: State, goal: Goal): Boolean {
        contextList.add(Context(actions.toMutableList(), state))
        while (true) {
            if (contextList.isEmpty()) break

            for (actionInContext in contextList.last().actions) {
                val s = contextList.last().state
                contextList.removeLast()

                val states = s.apply(actionInContext).toList()
                val actionMutable = actions.subList(1, actions.size)
                if (states.isNotEmpty() && actionMutable.isNotEmpty()) {
                    for (newState in states)
                        contextList.add(Context(actionMutable, newState))
                } else if (actionInContext == actions.last()) {
                    return if (states.isNotEmpty()) {
                        finalStateComplaintWithGoal(goal as FluentBasedGoal, states.first())
                    } else false
                } else break
            }
        }
        return false
    }
}
