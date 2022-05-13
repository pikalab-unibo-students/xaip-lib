interface Problem {
    val domain: Domain
    val objects: ObjectSet
    val initialState: State
    val goal: Goal
}