package impl

import Fluent
import FluentBasedGoal
import State

data class FluentBasedGoalImpl(override val fluent: Set<Fluent>) : FluentBasedGoal {
    override fun isSatisfiedBy(state: State): Boolean {
        TODO("Not yet implemented")
    }
}