package impl

import Action
import Applicable
import State
import java.util.*

internal data class ChoicePoint(
    val stack: Stack<Applicable<*>>,
    val state: State,
    val plan: MutableList<Action>
) {
    val depth: Int
        get() = stack.filterIsInstance<Action>().count()

    override fun toString(): String =
        """${ChoicePoint::class.simpleName}(
            |  ${ChoicePoint::state.name}=${state},
            |  ${ChoicePoint::state.name}.hash=${state.hashCode()},
            |  ${ChoicePoint::stack.name}=${stack.asReversed()},
            |  ${ChoicePoint::plan.name}=${plan}
            |)
        """.trimMargin()
}
