package dsl

import Fluent
import Object
import dsl.provider.PredicateProvider

/**
 * Class representing an [Fluent] in the DSL.
 */
class FluentDSL(
    private val predicateProvider: PredicateProvider
) {
    val fluents: MutableSet<Fluent> = mutableSetOf()

    private fun String.isCapital(): Boolean =
        isNotEmpty() && this[0] in 'A'..'Z'

    /**
     * Method that allow to treat a [String] as it was a [Fluent].
     */
    operator fun String.invoke(vararg args: String): Fluent =
        Fluent.of(
            predicateProvider.findPredicate(this, args.size)
                ?: error("Missing predicate: $this/${args.size}"),
            false,
            args.map {
                if (isCapital()) Variable.of(it) else Object.of(it)
            }
        )

    /**
     * Method that updates the internal list of [fluents] adding the last one created.
     */
    operator fun Fluent.unaryPlus() {
        fluents += this
    }
}
