package impl

import Fluent
import FluentBasedGoal
import State
import VariableAssignment
import it.unibo.tuprolog.core.Scope

internal data class FluentBasedGoalImpl(override val targets: Set<Fluent>) : FluentBasedGoal {
    override fun isSatisfiedBy(state: State): Boolean =
        targets.all { state.fluents.any(it::match) }

    override fun apply(substitution: VariableAssignment): FluentBasedGoal =
        FluentBasedGoal.of(targets.map { it.apply(substitution) }.toSet())

    override fun refresh(scope: Scope): FluentBasedGoal {
        return copy(targets = targets.map { it.refresh(scope) }.toSet())
    }

    override fun toString(): String =
        targets.map { it.toString() }.sorted().joinToString(", ", "goal(", ")")
}
