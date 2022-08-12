package dsl.provider

import Action
import Variable
import dsl.DomainDSL

class VariableProviderImpl(private val context: Any) {
    private val types: Map<String, Variable> = emptyMap()
    /*
    var ctx
        get() =
            when(context) {
                is Action->{
                    ctx.variables.associateBy { it.name }
                }
                is Axiom->{
                    ctx.variables.associateBy { it.name }
                }
            }

     */
}
