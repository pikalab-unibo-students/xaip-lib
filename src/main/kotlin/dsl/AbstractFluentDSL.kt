package dsl

import Fluent
import Object
import dsl.provider.PredicateProvider

abstract class AbstractFluentDSL(
    private val predicateProvider: PredicateProvider
) {
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
}
