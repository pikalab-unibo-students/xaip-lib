package explanation.impl

import Plan
import Problem
import explanation.GeneralQuestion
import explanation.Simulator

/**
 * Why not this plan instead.
 */
class Question4(
    override val problem: Problem,
    override val plan: Plan,
    val alternativePlan: Plan
) : GeneralQuestion {
    private val simulator = Simulator.of()
    override fun isValid(): Boolean =
        simulator.simulate(alternativePlan, problem.initialState, problem.goal)

    override fun `Where is the problem`(): Answer =
        simulator.simulate2(plan, problem.initialState, problem.goal)
}
