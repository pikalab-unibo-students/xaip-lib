import impl.StripsPlanner

interface Planner {
    fun plan(problem: Problem): Sequence<Plan>

    companion object {
        fun strips(): Planner = StripsPlanner()
    }
}
