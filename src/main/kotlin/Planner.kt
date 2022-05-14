import impl.PlannerImpl

interface Planner {
    fun plan(problem: Problem): Plan
    companion object {
        fun of(): Planner = PlannerImpl()
    }
}