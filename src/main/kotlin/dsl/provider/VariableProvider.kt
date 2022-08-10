package dsl.provider

import Variable

interface VariableProvider {
    fun findVariable(name: String): Variable?
}
