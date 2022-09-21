import impl.PlanImpl
/**
 * Method that represents the goal of the computation.
 * A plan consists of the set of actions need to reach the goal.
 *
 * @property actions: list of actions to be executed to achieve the goal from the initial state.
 */
interface Plan {
    val actions: List<Operator>

    companion object {
        /**
         * Factory method for an [Plan] creation.
         */
        fun of(actions: List<Operator>): Plan = PlanImpl(actions)
    }
}
