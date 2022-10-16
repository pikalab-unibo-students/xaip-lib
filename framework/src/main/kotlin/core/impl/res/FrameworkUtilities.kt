package core.impl.res

import core.Fluent
import core.FluentBasedGoal
import core.NotUnifiableException
import core.State
import it.unibo.tuprolog.core.Substitution

/**
 * core.Object providing some useful shared utilities.
 */
object FrameworkUtilities {

    /**
     * Method that emulate the ternary operator behaviour in kotlin.
     */
    infix fun <T> Boolean.then(param: T): T? = if (this) param else null
    private fun isUnificationPossible(fluent1: Fluent, fluent2: Fluent): Boolean {
        return when (
            val result = try {
                fluent1.mostGeneralUnifier(fluent2)
            } catch (_: NotUnifiableException) { null }
        ) {
            null -> false
            else -> result != Substitution.empty() && result != Substitution.empty()
        }
    }

    /**
     * Method that states if a [State] is compliant with a [Goal].
     */
    fun finalStateComplaintWithGoal(goal: FluentBasedGoal, currentState: State): Boolean {
        var count = 0
        for (fluent in goal.targets) {
            when (fluent.isGround) {
                true -> ((currentState.fluents.contains(fluent)) then count++) ?: count--
                else -> for (fluentState in currentState.fluents)
                    if (isUnificationPossible(fluentState, fluent)) {
                        count++
                        break
                    }
            }
        }
        return goal.targets.size == count
    }
}
