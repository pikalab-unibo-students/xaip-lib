package explanation

import Plan
import State
import explanation.impl.SimulatorImpl

/**
 *
 */
interface Simulator {
    /**
     *
     */
    fun simulate(plan: Plan, state: State): List<State>

    companion object {
        /**
         * Factory method for a [Simulator] creation.
         */
        fun of(): Simulator = SimulatorImpl()
    }
}
