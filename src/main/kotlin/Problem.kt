import impl.ProblemImpl
/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface Problem {
    val domain: Domain
    val objects: ObjectSet
    val initialState: State
    val goal: Goal

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(
            domain: Domain,
            objects: ObjectSet,
            initialState: State,
            goal: Goal
        ): Problem = ProblemImpl(domain, objects, initialState, goal)
    }
}
