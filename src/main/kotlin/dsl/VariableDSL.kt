package dsl

import Variable

/**
 * TODO non sono sicuro che serva questa classe
 * */
class VariableDSL {
    val variables: MutableSet<Variable> = mutableSetOf()

    /**
     * Method that allow to treat a [String] as it was a [Variable].
     */
    operator fun String.invoke(name: String): Variable {
        // occhio che il costruttore ammette la creazione anche a partire dai numeri
        return Variable.of(name)
    }

    /**
     * Method that updates the internal list of [variables] adding the last one created.
     */
    operator fun String.unaryPlus() { // stesso discorso dei tipi
        variables += Variable.of(this)
    }

    /**
     * Method that updates the internal list of [variables] adding the last one created.
     */
    operator fun Variable.unaryPlus() {
        variables += this
    }
}
