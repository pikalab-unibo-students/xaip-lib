package explanation.impl

import Plan
import State
import explanation.Simulator

class SimulatorImpl : Simulator {
    class Context(val state: State, val depth: Int = 0)

    private fun <T> MutableList<T>.pop(): T = removeAt(0)

    override fun simulate(plan: Plan, state: State): List<State> {
        val fringe: MutableList<Context> = mutableListOf(Context(state))
        while (fringe.none { it.depth < plan.actions.size  }) {
            val current = fringe.pop()
            val nextStates = current.state.apply(plan.actions[current.depth]).map { Context(it, current.depth + 1) }
            fringe.addAll(nextStates.toList())
        }
        return fringe.map { it.state }
    }

}
