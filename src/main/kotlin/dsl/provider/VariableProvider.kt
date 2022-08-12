package dsl.provider

import Variable

interface VariableProvider {
    fun findVariable(name: String): Variable?
    fun getVariables(): Map<String, Variable>

    companion object {
        fun of(context: Any): VariableProvider = VariableProviderImpl(context)
    }
}
