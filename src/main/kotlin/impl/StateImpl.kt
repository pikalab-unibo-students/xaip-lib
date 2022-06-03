package impl

import Action
import Fluent
import State
import VariableAssignment

internal data class StateImpl(override val fluents: Set<Fluent>) : State {

    init {
        require(fluents.all { it.isGround })
    }

    override fun apply(action: Action): State {
//        matchare ${action.preconditions} rispetto ai fluenti (applicando eventuali sostituzioni a tutta l'azione)
        val substitution = mguForActionPreconditions(action)
        val specificAction = action.apply(substitution)
//        rimuovere dai $fluents tutti gli effetti negativi (applicando eventuali sostituzioni allo stato)"

        val (addList, removeList) = specificAction.getAddAndRemoveLists()

        val fluentsIterator= fluents.toMutableSet().iterator()
        var fluents = mutableSetOf<Fluent>()
        while (fluentsIterator.hasNext())
            for (toBeRemoved in removeList) {
                val fluent= fluentsIterator.next()
                if (fluent == toBeRemoved) {
                    fluentsIterator.remove()
                }
                else if (fluent.match(toBeRemoved)) {
                    fluentsIterator.remove()
                    //  apply to substitution all fluents
                    fluents = fluentsIterator.asSequence().toMutableSet()
                    fluents.forEach { it -> it.apply(fluent.mostGeneralUnifier(toBeRemoved)) }
                }
        }
        //aggiungere ai $fluents tutti gli effetti positivi
        fluents.addAll(addList)
        return copy(fluents=fluents)
    }

    override fun apply(substitution: VariableAssignment): State =
        copy(fluents = fluents.map { it.apply(substitution) }.toSet())

    override fun isApplicable(action: Action): Boolean =
        action.preconditions.all { precondition ->
            fluents.any { precondition.match(it) }
        }

    private fun mguForActionPreconditions(action: Action): VariableAssignment =
        //ho un set di precondizioni
        action.preconditions.map { precondition ->
            //ognuna ha un set di fluent
            fluents.firstOrNull {
                //becco il primo che match con la precondizione e di questo calcolo l'mgu
                precondition.match(it) }?.mostGeneralUnifier(precondition) ?:
                error("Action $action is not applicable to state $this")
            //arrivata qui ho una sostituzione logica per ogni precondition e vado a fare la merge che me ne restituisce una sola
        }.reduce(VariableAssignment::merge)


    fun mguForActionPreconditionsAsSequence(action: Action) =
        action.preconditions.map { precondition ->
            fluents.filter {
                precondition.match(it)}.map {
                it.mostGeneralUnifier(precondition)
                }.reduce(VariableAssignment::merge)
            }

    fun mguForActionPreconditionsSet(action: Action): Set<VariableAssignment> {
        val substitutionOut: MutableSet<VariableAssignment> = mutableSetOf()
        action.preconditions.map { precondition ->
            val substitutions: MutableList<VariableAssignment> =  mutableListOf<VariableAssignment>()
            fluents.forEach {
                if (precondition.match(it)) {
                    substitutions.add(
                        it.mostGeneralUnifier(precondition)
                    )
                }
            }
            substitutionOut.add(
                substitutions.reduce(VariableAssignment::merge))
        }
        return substitutionOut
    }
    private fun Action.getAddAndRemoveLists(): Pair<Set<Fluent>, Set<Fluent>> {
        val addList: MutableSet<Fluent> = mutableSetOf()
        val removeList: MutableSet<Fluent> = mutableSetOf()
        for (effect in effects) {
            if (effect.isPositive) {
                addList.add(effect.fluent)
            } else {
                removeList.add(effect.fluent)
            }
        }
        return addList to removeList
    }

}