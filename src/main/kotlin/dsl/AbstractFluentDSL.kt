package dsl

import Fluent
import Object
import dsl.provider.PredicateProvider

/**
 * */
open class AbstractFluentDSL protected constructor(
    private val predicateProvider: PredicateProvider
) {
    /**
     * Method that allow to treat a [String] as it was a [Fluent].
     */
    operator fun String.invoke(vararg args: String): Fluent =
        Fluent.of(
            predicateProvider.findPredicate(this, args.size)
                ?: error("Missing predicate: $this/${args.size}"),
            false,
            args.map {
                if (isNotEmpty() && it[0].isUpperCase()) Variable.of(it)
                else Object.of(it)
            }
        )
}
