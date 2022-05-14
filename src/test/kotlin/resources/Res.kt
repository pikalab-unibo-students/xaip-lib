package resources

import State
import Type
import Value
import Object
import impl.*
import io.mockk.mockk
import it.unibo.tuprolog.core.Atom
object Res {
    fun getRandomInt(min: Int, max: Int): Int= (min..max).random()
    fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(length) { charset.random() }
            .joinToString("")
    }

    var name ="Giovanni"
    var size = 5
    val state= mockk<State>()
    val type1 = Type.of(name, null)

    val objEmpty= Object.of(Atom.of(""))
    val objNotEmpty=Object.of(Atom.of(name))

    val variableEmpty = Var.of("")
    val variableNotEmpty = Var.of(name)

    val predicateEmpty = Predicate.of("", emptyList())
    val predicateNotEmpty = Predicate.of(name, List<Type>(size){type1})

    val fluentEmpty = Fluent.of("", emptyList(), predicateEmpty, false)
    var fluentNotEmpty = Fluent.of(name, List<Value>(size){ variableNotEmpty}, predicateNotEmpty, true)

    val effectEmpty= Effect.of(fluentEmpty, false)
    val effectNotEmpty= Effect.of(fluentNotEmpty, true)

    val axiomEmpty = Axiom.of(emptyMap(), emptySet(), emptySet())
    val axiomNotEmpty = Axiom.of(mapOf(variableNotEmpty to type1 ), setOf(fluentNotEmpty), setOf(fluentNotEmpty))

    val actionEmpty= Action.of("", emptyMap(), emptySet(), emptySet())
    var actionNotEmpty = Action.of(name, mapOf(variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(effectNotEmpty))

    val domainEmpty = Domain.of("", emptySet(), emptySet(),emptySet(), emptySet())
    val domainNotEmpty = Domain.of(name, setOf(predicateNotEmpty), setOf(actionNotEmpty),setOf(type1), setOf(axiomNotEmpty))

    val objectSetEmpty= ObjectSet.of(emptyMap())
    val objectSetNotEmpty= ObjectSet.of(mapOf(type1 to setOf(objNotEmpty)))

    val planEmpty = Plan.of(emptyList())
    val planNotEmpty = Plan.of(listOf(actionNotEmpty))
}