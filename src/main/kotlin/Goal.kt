/**
 * The goal is a logical expression of predicates which must be satisfied
 * in order for a plan to be considered a solution.
 */
interface Goal {
    fun isSatisfiedBy(state: State): Boolean
}

