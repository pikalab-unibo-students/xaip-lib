package explanation

import Plan
import State

interface Simulator {
    fun simulate(plan: Plan, state: State): List<State>
}
