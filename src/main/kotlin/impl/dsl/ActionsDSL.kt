package impl.dsl

import Action
import Fluent
import Object
import Type
import Variable

/**
 * Class representing an [Action] in the DSL.
 */
class ActionsDSL(
    private val predicateProvider: PredicateProvider
) {
    val actions: MutableList<Action> = mutableListOf()

    operator fun String.invoke(f: ActionDSL.() -> Unit) {
        actions += ActionDSL(predicateProvider).also(f).toAction(this)
    }
}

class ActionDSL(
    private val predicateProvider: PredicateProvider
) {

    var parameters: Map<Variable, Type> = mapOf()

    var preconditions: MutableSet<Fluent> = mutableSetOf()

    fun toAction(name: String): Action = TODO()

    fun params(f: ParametersDSL.() -> Unit) {
        parameters = ParametersDSL().also(f).parameters
    }

    fun preconditions(f: FluentDSL.() -> Unit) {
        preconditions += FluentDSL(predicateProvider).also(f).fluents
    }
}

class FluentDSL(
    private val predicateProvider: PredicateProvider
) {
    val fluents: MutableSet<Fluent> = mutableSetOf()

    private fun String.isCapital(): Boolean =
        isNotEmpty() && this[0] in 'A'..'Z'

    operator fun String.invoke(vararg args: String): Fluent =
        Fluent.of(
            predicateProvider.findPredicate(this, args.size)
                ?: error("Missing predicate: $this/${args.size}"),
            false,
            args.map {
                if (isCapital()) Variable.of(it) else Object.of(it)
            }
        )

    operator fun Fluent.unaryPlus() {
        fluents += this
    }
}

class ParametersDSL {

    val parameters: MutableMap<Variable, Type> = mutableMapOf()

    infix fun String.ofType(type: String) {
        val variable = Variable.of(this)
        val t = Type.of(type)
        parameters[variable] = t
    }
}

fun main() {
    domain {
        name = "block_world"
        types {
            +"any"
            +"string"("any")
            +"block"("string")
        }
        predicates {
            +"on"("block", "block")
        }
        actions {
            "stack" {
                preconditions {
                    +"on"("a", "b")
                    +"on"("a", "b")
                }
            }
        }
    }
}
