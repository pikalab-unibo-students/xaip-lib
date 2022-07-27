import impl.StripsPlanner
/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface Planner {
    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun plan(problem: Problem): Sequence<Plan>

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun strips(): Planner = StripsPlanner()
    }
}
