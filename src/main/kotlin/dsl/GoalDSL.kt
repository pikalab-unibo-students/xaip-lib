package dsl

import FluentBasedGoal
import dsl.provider.PredicateProvider

/**
 * Class representing a [FluentBasedGoal] in the DSL.
 */
class GoalDSL(
    private val predicateProvider: PredicateProvider
) {
    var goals: MutableSet<FluentBasedGoal> = mutableSetOf()
    operator fun String.invoke(vararg targets: String) {
        for (fluent in targets) {
            if (predicateProvider.findPredicate(this, 0) == null) error("Predicate does not exist")
            // Problema io non so dove tirarmi fuori gli altri parametri per istanziare il Fluent, non ho l'arità, non ho i valori
            /*
            Fluent.of(
                Predicate.of(this, ???),
                isNegated??
                targets
            )
            */
        }
        TODO("Restituisci il Goal")
    }

    /**
     * Method that updates the internal list of [goals] adding the last one created.
     */
    operator fun FluentBasedGoal.unaryPlus() {
        goals += this
    }
}
