package dsl.provider

import Variable

/**
 * Provider of the variables created in each instance of [Action] and [Axiom].
 */
interface VariableProvider {
    /**
     * Method that given a [name] retrieves it if it exists and returns null otherwise.
     */
    fun findVariable(name: String): Variable?

    /**
     * Method that returns all the variables declared.
     */
    fun getVariables(): Map<String, Variable>

    /**
     * Method that add a new variable to the ones already saved in the provider.
     * */
    fun addVariable(variable: Variable)

    companion object {
        /**
         * Factory method for an [VariableProvider] creation.
         */
        fun of(): VariableProvider = VariableProviderImpl()
    }
}
