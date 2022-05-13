package impl

import Action
import Fluent
import State
import Substitution

// f(2), f(1), f(3)

// P: f(X), A: f(X+1), R: f(X), f(X-1)
// X = 2
// P: f(2), A: f(3), R: f(1), f(2)
data class StateImpl(override val fluents: Set<Fluent>) : State {

    init {
        require(fluents.all { it.isGround })
    }

    override fun apply(action: Action): State {
//        matchare ${action.preconditions} rispetto ai fluenti (applicando eventuali sostituzioni a tutta l'azione)
        val substitution = mguForActionPreconditions(action)
        val specificAction = action.apply(substitution)
//        rimuovere dai $fluents tutti gli effetti negativi (applicando eventuali sostituzioni allo stato)"

        val (addList, removeList) = specificAction.getAddAndRemoveLists()
        val fluents = fluents.toMutableSet()
        for (fluent in fluents) { // TODO use iterators
            for (toBeRemoved in removeList) {
                if (fluent == toBeRemoved) {
                    fluents.remove(fluent)
                } else if (fluent.match(toBeRemoved)) {
                    fluents.remove(fluent)
                    val substitution = fluent.mgu(toBeRemoved)
                    // apply to substitution all fluents
                }
            }
        }
//        aggiungere ai $fluents tutti gli effetti positivi
        fluents.addAll(addList)
        return copy(fluents=fluents)
    }

    override fun apply(substitution: Substitution): State =
        copy(fluents = fluents.map { it.apply(substitution) }.toSet())

    override fun isApplicable(action: Action): Boolean =
        action.preconditions.all { precondition ->
            fluents.any { precondition.match(it) }
        }

    private fun mguForActionPreconditions(action: Action): Substitution =
        action.preconditions.map { precondition ->
            fluents.firstOrNull { precondition.match(it) }?.mgu(precondition) ?:
                error("Action $action is not applicable to state $this")
        }.reduce(Substitution::merge)

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