package explanation.impl

import Domain
import FluentBasedGoal
import NotUnifiableException
import Operator
import Plan
import Problem
import State
import explanation.Question
import explanation.Simulator
import it.unibo.tuprolog.core.Substitution

/**
 * Why not this plan instead.
 */
class Question4(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    val alternativePlan: Plan,
    override val focusOn: Int
) : Question, AbstractQuestion() {
    val simulator = Simulator.of()
    override fun buildHdomain(): Domain = throw UnsupportedOperationException()

    override fun buildHproblem(): Problem = throw UnsupportedOperationException()

    private fun finalStateComplaintWithGoal(goal: FluentBasedGoal, currentState: State): Boolean {
        var indice = 0
        for (fluent in goal.targets) {
            if (!fluent.isGround) {
                for (fluentState in currentState.fluents) {
                    val tmp = try {
                        fluentState.mostGeneralUnifier(fluent)
                    } catch (_: NotUnifiableException) { null }
                    // TODO(Se questa roba è sensata va fixata anche nell'altro modulo)
                    if (tmp != Substitution.empty() && tmp != Substitution.empty() && tmp != null) {
                        indice++
                        break
                    }
                }
            } else {
                if (currentState.fluents.contains(fluent)) indice++ else indice--
            }
        }
        return goal.targets.size == indice
    }

    override fun isPlanValid(): Boolean {
        val states = simulator.simulate(alternativePlan, problem.initialState)
        var flag = false
        if(states.isNotEmpty()){
            for(state in states) {
                val tmp = finalStateComplaintWithGoal(problem.goal as FluentBasedGoal, state)
                if(tmp) flag = true
            }

        }
        return flag
    }
}
