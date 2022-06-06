package impl

import Action
import Fluent
import State
import VariableAssignment
import impl.res.toPddl
import impl.res.toTerm
import it.unibo.tuprolog.core.Fact
import it.unibo.tuprolog.core.Tuple
import it.unibo.tuprolog.solve.Solution
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.theory.Theory

internal data class StateImpl(override val fluents: Set<Fluent>) : State {

    init {
        require(fluents.all { it.isGround }) {
            "States cannot contain non-ground fluents, while the following fluents were provided: $fluents"
        }
    }

    override fun apply(action: Action): Sequence<State> =
        mguForActionPreconditions(action).map { action.apply(it) }.map {
            val (addList, removeList) = it.getAddAndRemoveLists()
            val fluents = (fluents - removeList) + addList
            State.of(fluents)
        }

    override fun apply(substitution: VariableAssignment): State =
        copy(fluents = fluents.map { it.apply(substitution) }.toSet())

    override fun isApplicable(action: Action): Boolean =
        action.preconditions.all { precondition ->
            fluents.any { precondition.match(it) }
        }

    private fun mguForActionPreconditions(action: Action): Sequence<VariableAssignment> {
        // Convert the current state into a Prolog theory where each fluent is a fact
        val stateAsTheory = Theory.of(fluents.map { it.toTerm() }.map { Fact.of(it) })
        // Convert the current action into a Prolog goal where each precondition is a sub-goal
        val preconditionsAsQuery = Tuple.of(action.preconditions.map { it.toTerm() })
        // Then create a Prolog solver out of the aforementioned theory ...
        return Solver.prolog.solverOf(stateAsTheory)
            // ... and query the solver with the aforementioned query ...
            .solve(preconditionsAsQuery)
            // ... then consider only positive answers ...
            .filterIsInstance<Solution.Yes>()
            // ... and finally convert each answer's substitution into a VariableAssignment
            .map { it.substitution.toPddl() }
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

    override fun toString(): String =
        fluents.map { it.toString() }.sorted().joinToString(", ", "state(", ")")
}