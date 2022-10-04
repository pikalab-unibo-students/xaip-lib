package explanation.impl // ktlint-disable filename

import FluentBasedGoal
import Goal
import NotUnifiableException
import Operator
import Plan
import State
import explanation.Answer
import explanation.Simulator
import it.unibo.tuprolog.core.Substitution

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

    /**
     * Idea:
     * Tenere una lista dei rami possibili da esplorare.
     * 1. Inizializzazione della lista
     * 2. Controllo se non ho più rami da esplorare esco e ritorno false.
     * 3. Altrimenti inizio ad eseguire un ciclo sulla lista dei rami da esplorare.
     * 4. rimuovo il ramo che sto analizzando
     * 5. applico l'azione
     * 6. creo una lista delle azioni ancora da valutare
     * 7. se l'azione era applicabile e ci sono ancora azioni da applicare
     *    allora creo un nuovo punto di scelta con il nuovo wstato e la lista delle rimanenti azioni.
     * 8. se l'azione era l'ultima allora allora controllo
     *    a. se era applicabile allora controllo se soddisfa il goal
     *    b. altrimento ritorno false
     *
     * */
    override fun simulate(plan: Plan, state: State, goal: Goal): Boolean {
        val actions = plan.actions
        // 1.
        contextList.add(Context(actions.toMutableList(), state))
        while (true) {
            // 2.
            if (contextList.isEmpty()) break
            // 3.
            for (actionInContext in contextList.first().actions) {
                val s = contextList.first().state
                // 5.
                val states = s.apply(actionInContext).toList()
                // 6.
                val actionMutable = contextList.first().actions.subList(1, contextList.first().actions.size)
                // 4.
                contextList.removeFirst()
                // 7.
                if (states.isNotEmpty() && actionMutable.isNotEmpty()) {
                    for (newState in states)
                        contextList.add(Context(actionMutable, newState))
                    // 8.
                } else if (actionInContext == actions.last()) {
                    return if (states.isNotEmpty()) {
                        finalStateComplaintWithGoal(goal as FluentBasedGoal, states.first())
                    } else false
                }
            }
        }
        return false
    }

    override fun simulate2(plan: Plan, state: State, goal: Goal): Answer {
        val actions = plan.actions
        // 1.
        contextList.add(Context(actions.toMutableList(), state))
        while (true) {
            // 2.
            if (contextList.isEmpty()) return Answer(false)
            // 3.
            for (actionInContext in contextList.first().actions) {
                val s = contextList.first().state
                // 5.
                val states = s.apply(actionInContext).toList()
                // 6.
                val actionMutable = contextList.first().actions.subList(1, contextList.first().actions.size)
                // 4.
                contextList.removeFirst()
                // 7.
                if (states.isNotEmpty() && actionMutable.isNotEmpty()) {
                    for (newState in states)
                        contextList.add(Context(actionMutable, newState))
                    // 8.
                } else if (actionInContext == actions.last()) {
                    return if (states.isNotEmpty() && finalStateComplaintWithGoal(goal as FluentBasedGoal, states.first())){
                        Answer(true)
                    } else Answer(false, actionInContext)
                }
            }
        }
    }
}
