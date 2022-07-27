import impl.ProblemImpl

interface Problem {
    val domain: Domain
    val objects: ObjectSet
    val initialState: State
    val goal: Goal

    companion object {
        fun of(
            domain: Domain,
            objects: ObjectSet,
            initialState: State,
            goal: Goal
        ): Problem = ProblemImpl(domain, objects, initialState, goal)
    }
}
