package explanation.impl

import FluentBasedGoal
import Goal
import Operator
import State

/**
 *
 */
class Simulator {
    class Context(val actions: List<Operator>, val state: State)

    var contextList: MutableList<Context> = mutableListOf()

    fun simulate(actions: List<Operator>, state: State, goal: Goal): Boolean {
        contextList.add(Context(actions.toMutableList(), state))
        while (true) {
            if (contextList.isEmpty()) break

            for (actionInContext in contextList.last().actions) {
                val s = contextList.last().state
                contextList.removeLast()

                val states = s.apply(actionInContext)
                if (!states.toList().isEmpty()) {
                    val actionMutable = actions.subList(1, actions.size)
                    for (newState in states)
                        contextList.add(Context(actionMutable, newState))
                } else break
                if(actionInContext == contextList.last().actions.last())
                    return states.first().fluents.containsAll((goal as FluentBasedGoal).targets)
            }
        }
        return false
    }
}
