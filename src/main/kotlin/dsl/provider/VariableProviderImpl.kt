package dsl.provider

import Variable

/**
 *
 */
class VariableProviderImpl() : VariableProvider {
    private var variables: MutableMap<String, Variable> = mutableMapOf()

    override fun findVariable(name: String): Variable? {
        return variables[name]
    }

    override fun getVariables(): Map<String, Variable> = this.variables

    override fun addVariable(variable: Variable) {
        this.variables[variable.name] = variable
    }
}
