package dsl.provider

import Action
import Axiom
import Variable

class VariableProviderImpl(context: Any): VariableProvider {
    private var variables: Map<String, Variable> = emptyMap()

    init {
        when (context) {
            is Action -> {
                variables = context.variables.associateBy { it.name }
            }
            is Axiom -> {
                variables = context.variables.associateBy { it.name }
            }
        }

    }

    override fun findVariable(name: String): Variable? {
        return variables[name]
    }

    override fun getVariables(): Map<String, Variable> = this.variables

}
