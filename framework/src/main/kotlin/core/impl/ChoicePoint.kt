package core.impl

import core.Applicable
import core.Operator
import core.State
import java.util.* // ktlint-disable no-wildcard-imports

internal data class ChoicePoint(
    val stack: Stack<Applicable<*>>,
    val state: State,
    val plan: MutableList<Operator>
) {
    val depth: Int
        get() = stack.filterIsInstance<Operator>().count()

    override fun toString(): String =
        """${ChoicePoint::class.simpleName}(
            |  ${ChoicePoint::state.name}=$state,
            |  ${ChoicePoint::state.name}.hash=${state.hashCode()},
            |  ${ChoicePoint::stack.name}=${stack.asReversed()},
            |  ${ChoicePoint::plan.name}=$plan
            |)
        """.trimMargin()
}
