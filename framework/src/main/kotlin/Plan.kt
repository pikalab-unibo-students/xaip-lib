import impl.PlanImpl
/**
 * Method that represents the goal of the computation.
 * A plan consists of the set of actions need to reach the goal.
 */
interface Plan {
    /**
     * @property operators: list of actions to be executed to achieve the goal from the initial state.
     */
    val operators: List<Operator>

    companion object {
        /**
         * Factory method for an [Plan] creation.
         */
        fun of(actions: List<Operator>): Plan = PlanImpl(actions)
    }
}
