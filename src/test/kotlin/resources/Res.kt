package resources

import State
import Type
import Value
import impl.*
import io.mockk.mockk

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

    val value1 = ValueImpl()
    val type1 = TypeImpl()

    val objEmpty= ObjectImpl("")
    val objNotEmpty=ObjectImpl(name)

    val variableEmpty = VarImpl("")
    val variableNotEmpty = VarImpl(name)

    val predicateEmpty = PredicateImpl("", emptyList())
    val predicateNotEmpty = PredicateImpl(name, List<Type>(size){type1})

    val fluentEmpty = FluentImpl("", emptyList(), predicateEmpty, false)
    var fluentNotEmpty = FluentImpl(name, List<Value>(size){value1}, predicateNotEmpty, true)

    val effectEmpty= EffectImpl(fluentEmpty, false)
    val effectNotEmpty= EffectImpl(fluentNotEmpty, true)

    val axiomEmpty = AxiomImpl(emptyMap(), emptySet(), emptySet())
    val axiomNotEmpty = AxiomImpl(mapOf(variableNotEmpty to type1 ), setOf(fluentNotEmpty), setOf(fluentNotEmpty))

    val actionEmpty= ActionImpl("", emptyMap(), emptySet(), emptySet())
    var actionNotEmpty = ActionImpl(name, mapOf(variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(effectNotEmpty))

    val domainEmpty = DomainImpl("", emptySet(), emptySet(),emptySet(), emptySet())
    val domainNotEmpty = DomainImpl(name, setOf(predicateNotEmpty), setOf(actionNotEmpty),setOf(type1), setOf(axiomNotEmpty))

}