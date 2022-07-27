import impl.PlanImpl
/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface Plan {
    val actions: List<Action>

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(actions: List<Action>): Plan = PlanImpl(actions)
    }
}
