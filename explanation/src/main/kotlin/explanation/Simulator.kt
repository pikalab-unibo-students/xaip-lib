package explanation

import Goal
import Plan
import State

interface Simulator {
    fun simulate(plan: Plan, state: State, goal: Goal): Boolean
}
