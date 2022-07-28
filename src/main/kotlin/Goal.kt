/**
 * A generic interface for the [Goal] of a [Problem].
 */
interface Goal {
    /***
     * Method that states if a state satisfy the given [Goal].
     */
    fun isSatisfiedBy(state: State): Boolean
}
