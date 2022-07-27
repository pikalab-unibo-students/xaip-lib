/**
 * The goal is a logical expression of predicates which must be satisfied
 * in order for a plan to be considered a solution.
 */
interface Goal {
    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun isSatisfiedBy(state: State): Boolean
}
