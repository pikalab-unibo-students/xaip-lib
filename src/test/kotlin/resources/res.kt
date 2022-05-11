package resources

import State
import impl.*
import io.mockk.mockk

object res {
    const val nameGC ="Giovanni"
    val state= mockk<State>()

    val value1 = ValueImpl()
    val type1 = TypeImpl()
    val objEmpty= ObjectImpl("")
    val objNotEmpty=ObjectImpl(nameGC)
    val variableEmpty = VarImpl("")
    val variableNotEmpty = VarImpl(nameGC)
    val predicateEmpty = PredicateImpl("", emptyList())
    val predicateNotEmpty = PredicateImpl(nameGC, listOf(type1))
    val fluentEmpty = FluentImpl("", emptyList(), predicateEmpty, false)
    val fluentNotEmpty = FluentImpl(nameGC, listOf(value1), predicateNotEmpty, true)
    val effectEmpty= EffectImpl(fluentEmpty, false)
    val effectNotEmpty= EffectImpl(fluentEmpty, true)
    val axiomEmpty = AxiomImpl(emptyMap(), emptySet(), emptySet())
    val axiomNotEmpty = AxiomImpl(mapOf(variableNotEmpty to type1 ), setOf(fluentNotEmpty), setOf(fluentNotEmpty))
    val actionEmpty= ActionImpl("", emptyMap(), emptySet(), emptySet())
    val actionNotEmpty =ActionImpl(nameGC, mapOf(variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(
        effectNotEmpty))
    val domainEmpty = DomainImpl("", emptySet(), emptySet(),emptySet(), emptySet())
    val domainNotEmpty = DomainImpl(nameGC, setOf(predicateNotEmpty), setOf(actionNotEmpty),setOf(type1), setOf(axiomNotEmpty))

}