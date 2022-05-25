package impl

import Fluent
import FluentBasedGoal
import State
import VariableAssignment

internal data class FluentBasedGoalImpl(override val targets: Set<Fluent>) : FluentBasedGoal {
    override fun isSatisfiedBy(state: State): Boolean =
        targets.all { state.fluents.any(it::match) }

    override fun apply(substitution: VariableAssignment): FluentBasedGoal =
        FluentBasedGoal.of(targets.map { it.apply(substitution) }.toSet())

}