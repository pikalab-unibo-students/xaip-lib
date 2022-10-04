package explanation.impl

import Plan
import Problem
import explanation.GeneralQuestion

/**
 * Why is plan not good (solvable).
 */
class Question5(
    override val problem: Problem,
    override val plan: Plan
) : GeneralQuestion
