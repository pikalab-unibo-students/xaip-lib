package dsl

import Object

/**
 * Class representing an [Object] in the DSL.
 */
class ObjectDSL {
    val objects: MutableSet<Object> = mutableSetOf()

    /**
     * Method that allow to treat a [String] as it was a [Object].
     */
    operator fun String.invoke(name: String): Object {
        // occhio che il costruttore ammette la creazione anche a partire dai numeri
        return Object.of(name)
    }

    /**
     * Method that updates the internal list of [objects] adding the last one created.
     */
    operator fun String.unaryPlus() { // stesso discorso dei tipi
        objects += Object.of(this)
    }

    /**
     * Method that updates the internal list of [objects] adding the last one created.
     */
    operator fun Object.unaryPlus() {
        objects += this
    }
}
