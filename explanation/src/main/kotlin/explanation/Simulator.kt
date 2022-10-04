package explanation

import Goal
import Operator
import Plan
import State

interface Simulator {
    fun simulate(plan: Plan, state: State, goal: Goal): Boolean
    fun simulate2(plan: Plan, state: State, goal: Goal): Answer
}

class Answer(val planIsAcceptable: Boolean, val operator: Operator? = null)