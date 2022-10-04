package explanation.impl

import Plan
import Problem
import explanation.GeneralQuestion
import explanation.Simulator

/**
 * Why is plan not good (solvable).
 */
class Question5(
    override val problem: Problem,
    override val plan: Plan
) : GeneralQuestion {

    private val simulator = Simulator.of()
    override fun isValid(): Boolean =
        simulator.simulate(plan, problem.initialState, problem.goal)

    override fun `Where is the problem`(): Answer =
        simulator.simulate2(plan, problem.initialState, problem.goal)
}
