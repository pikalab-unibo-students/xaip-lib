package explanation.impl

import FluentBasedGoal
import Goal
import Operator
import State

interface Simulator {
    fun simulate(actions: List<Operator>, state: State, goal: Goal): Boolean
}

/**
 *
 */
class SimulatorImpl() : Simulator {
    class Context(val actions: List<Operator>, val state: State)

    var contextList: MutableList<Context> = mutableListOf()

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
                    val fluents = states.first().fluents
                    return fluents.containsAll((goal as FluentBasedGoal).targets)
                } else break
            }
        }
        return false
    }
}
