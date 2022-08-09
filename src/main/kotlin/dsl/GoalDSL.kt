package dsl

import FluentBasedGoal
import Goal
import dsl.provider.PredicateProvider

/**
 * Class representing a [FluentBasedGoal] in the DSL.
 */
// TODO i believe you can exploit AbstractFluentDSL to implement this class
class GoalDSL(
    private val predicateProvider: PredicateProvider
) {

    // TODO secondo me GoalDSL deve creare un unico FluentBaseGoal, composto di più fluent
    var goals: MutableSet<FluentBasedGoal> = mutableSetOf()

    /**
     * */
    operator fun String.invoke(vararg targets: String): Goal {
        TODO("se segui il suggerimento sopra, secondo me questo metodo non ti serve")
    }

    /**
     * Method that updates the internal list of [goals] adding the last one created.
     */
    operator fun Goal.unaryPlus() {
        TODO("se segui il suggerimento sopra, questo metodo si applica ai fluent, li converte in goal, e salva i goal")
    }
}
