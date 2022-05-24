package impl

import Fluent
import FluentBasedGoal
import State
import Substitution

internal data class FluentBasedGoalImpl(override val fluents: Set<Fluent>) : FluentBasedGoal {
    override fun isSatisfiedBy(state: State): Boolean {
        TODO("Not yet implemented")
    }

    override fun apply(substitution: Substitution): FluentBasedGoal =
        FluentBasedGoal.of(fluents.map { it-> it.apply(substitution) }.toSet())
}