package explanation

import Goal
import Plan
import State
import explanation.impl.Answer
import explanation.impl.SimulatorImpl

interface Simulator {
    fun simulate(plan: Plan, state: State, goal: Goal): Boolean
    fun simulate2(plan: Plan, state: State, goal: Goal): Answer

    companion object {
        /**
         * Factory method for an [Simulator] creation.
         */
        fun of(): Simulator = SimulatorImpl()
    }
}
