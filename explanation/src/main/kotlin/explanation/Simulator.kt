package explanation
import core.Plan
import core.State
import explanation.impl.SimulatorImpl

/**
 * A [Simulator] able to simulate the execution of a [Plan].
 */
interface Simulator {
    /**
     * Method used to simulate the execution of a [Plan].
     */
    fun simulate(plan: Plan, state: State): List<State>

    companion object {
        /**
         * Factory method for a [Simulator] creation.
         */
        fun of(): Simulator = SimulatorImpl()
    }
}
