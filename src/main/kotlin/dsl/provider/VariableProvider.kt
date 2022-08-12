package dsl.provider

import Variable

interface VariableProvider {
    fun findVariable(name: String): Variable?
    fun getVariables(): Map<String, Variable>
}
