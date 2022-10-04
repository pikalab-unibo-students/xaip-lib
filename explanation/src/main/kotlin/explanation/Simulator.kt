package explanation

import Plan
import State

interface Simulator {
    fun simulate2(plan: Plan, state: State): List<State>
}
