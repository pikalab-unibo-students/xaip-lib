package explanation.impl

import Operator
import State

/**
 *
 */
class Simulator {
    class Context(val actions: List<Operator>, val state: State)

    var contextList: MutableList<Context> = mutableListOf()

    fun simulate(actions: List<Operator>, state: State) {
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
            }
        }
    }
}
