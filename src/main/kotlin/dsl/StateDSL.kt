package dsl

import State

/**
 * Class representing a [State] in the DSL.
 */
class StateDSL {
    val states: MutableSet<State> = mutableSetOf()

    /**
     * Method that allow to treat a [String] as it was a [State].
     */
    operator fun String.invoke(vararg fluents: String): State {
        for (fluent in fluents) {
            TODO("Capisci come cavolo creare dei Fluent a partire da delle stringhe")
        }
        TODO("Una volta che hai i fluent istanzia lo stato")
    }

    /**
     * Method that updates the internal list of [types] adding the last one created.
     */
    operator fun State.unaryPlus() {
        states += this
    }
}
