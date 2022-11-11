package core.impl

import core.Domain
import core.Goal
import core.ObjectSet
import core.Problem
import core.State

internal data class ProblemImpl(
    override val domain: Domain,
    override val objects: ObjectSet,
    override val initialState: State,
    override val goal: Goal
) : Problem {
    override fun toString(): String =

        """${Problem::class.simpleName}(
            |  ${Problem::domain.name}=${domain.toString().replace("\n", "\n  ")},
            |  ${Problem::objects.name}=$objects,
            |  ${Problem::initialState.name}=$initialState,
            |  ${Problem::goal.name}=$goal,
            |)
        """.trimMargin()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProblemImpl

        if (domain != other.domain) return false
        if (objects != other.objects) return false
        if (initialState != other.initialState) return false
        if (goal != other.goal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = domain.hashCode()
        result = 31 * result + objects.hashCode()
        result = 31 * result + initialState.hashCode()
        result = 31 * result + goal.hashCode()
        return result
    }
}
