package explanation.impl

import FluentBasedGoal
import Goal
import NotUnifiableException
import Operator
import Plan
import State
import it.unibo.tuprolog.core.Substitution

interface Simulator {
    fun simulate(pla: Plan, state: State, goal: Goal): Boolean
}

/**
 *
 */
class SimulatorImpl() : Simulator {
    class Context(val actions: List<Operator>, val state: State)

    var contextList: MutableList<Context> = mutableListOf()

    // NB copiata dall'altro modulo
    private fun finalStateComplaintWithGoal(goal: FluentBasedGoal, currentState: State): Boolean {
        var indice = 0
        for (fluent in goal.targets) {
            if (!fluent.isGround) {
                for (fluentState in currentState.fluents) {
                    val tmp = try {
                        fluentState.mostGeneralUnifier(fluent)
                    } catch (_: NotUnifiableException) { null }
                    if (tmp != Substitution.empty() || tmp != Substitution.empty()) {
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

    /**
     * Idea:
     * Tenere una lista dei rami possibili da esplorare.
     * 1. Inizializzazione della lista
     * 2. Controllo se non ho più rami da esplorare esco e ritorno false.
     * 3. Altrimenti inizio ad eseguire un ciclo sulla lista dei rami da esplorare.
     * */
    override fun simulate(plan: Plan, state: State, goal: Goal): Boolean {
        val actions = plan.actions
        // 1.
        contextList.add(Context(actions.toMutableList(), state))
        while (true) {
            // 2.
            if (contextList.isEmpty()) break
            // 3.
            for (actionInContext in contextList.also { it.reverse() }.last().actions) {
                val s = contextList.last().state
                contextList.removeLast()

                val states = s.apply(actionInContext).toList()
                val actionMutable = actions.subList(1, actions.size)
                if (states.isNotEmpty() && actionMutable.isNotEmpty()) {
                    for (newState in states)
                        contextList.add(Context(actionMutable, newState))
                } else if (actionInContext == actions.last()) {
                    return if (states.isNotEmpty()) {
                        finalStateComplaintWithGoal(goal as FluentBasedGoal, states.first())
                    } else false
                } else break
            }
        }
        return false
    }
}
