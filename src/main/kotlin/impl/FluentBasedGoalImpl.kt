package impl

import Fluent
import FluentBasedGoal
import State

class FluentBasedGoalImpl(override val fluent: Set<Fluent>) : FluentBasedGoal {
    override fun isStatisfiedBy(state: State): Boolean {
        TODO("Not yet implemented")
    }
}