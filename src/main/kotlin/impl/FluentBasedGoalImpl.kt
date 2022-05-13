package impl

import Fluent
import FluentBasedGoal
import State
import Substitution

internal data class FluentBasedGoalImpl(override val fluent: Set<Fluent>) : FluentBasedGoal {
    override fun isSatisfiedBy(state: State): Boolean {
        TODO("Not yet implemented")
    }

    override fun apply(substitution: Substitution): FluentBasedGoal {
        TODO("Not yet implemented")
    }
}