import impl.PlannerImpl
import impl.StateImpl

interface Planner {
    fun plan(problem: Problem): Plan
    companion object {
        fun of(): Planner = PlannerImpl()
    }
}