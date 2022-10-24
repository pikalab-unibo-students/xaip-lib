package core.resources

import core.* // ktlint-disable no-wildcard-imports

object TestUtils {
    const val name = "F"
    const val size = 5

    val type1 = Type.of(name, null)

    val objEmpty = Object.of("")
    val objNotEmpty = Object.of(name)

    val variableNotEmpty = Variable.of(name)

    val predicateEmpty = Predicate.of("", emptyList())
    val predicateNotEmpty = Predicate.of(name, List(size) { type1 })

    val fluentEmpty = Fluent.of(predicateEmpty, false, emptyList())
    var fluentNotEmpty = Fluent.of(predicateNotEmpty, true, List<Value>(size) { variableNotEmpty })

    val effectEmpty = Effect.of(fluentEmpty, false)
    val effectNotEmpty = Effect.of(fluentNotEmpty, true)

    val axiomNotEmpty = Axiom.of(mapOf(variableNotEmpty to type1), fluentNotEmpty, fluentNotEmpty)

    val actionEmpty = Action.of("", emptyMap(), emptySet(), emptySet())
    var actionNotEmpty = Action.of(
        name,
        mapOf(variableNotEmpty to type1),
        setOf(fluentNotEmpty),
        setOf(effectNotEmpty)
    )

    val domainEmpty = Domain.of("", emptySet(), emptySet(), emptySet())
    val domainNotEmpty =
        Domain.of(name, setOf(predicateNotEmpty), setOf(actionNotEmpty), setOf(type1), axiomNotEmpty)

    val objectSetEmpty = ObjectSet.of(emptyMap())
    val objectSetNotEmpty = ObjectSet.of(mapOf(type1 to setOf(objNotEmpty)))

    val planEmpty = Plan.of(emptyList())
    val planNotEmpty = Plan.of(listOf(Operator.of(actionNotEmpty)))

    val substitution = VariableAssignment.of(variableNotEmpty, variableNotEmpty)
}
