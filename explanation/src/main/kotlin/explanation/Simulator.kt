package explanation
import Plan
import State
import explanation.impl.SimulatorImpl

/**
 * Entity able to simulate the execution of a [Plan] chosen.
 */
interface Simulator {
    /**
     * Method to simulate the exection of a [Plan].
     */
    fun simulate(plan: Plan, state: State): List<State>

    companion object {
        /**
         * Factory method for a [Simulator] creation.
         */
        fun of(): Simulator = SimulatorImpl()
    }
}
