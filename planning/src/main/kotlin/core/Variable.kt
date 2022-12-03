package core

import core.impl.Context
import core.impl.VariableImpl
/**
 * Entity responsible for the parameterisation of an [Action].
 * Variables that stand for terms of the [Problem] instance; they are instantiated to [Object] from
 * a specific problem instance when an [Action] is grounded for application.
 */
interface Variable : Value {
    /**
     * @property name: name of the [Variable].
     */
    val name: String

    /**
     * Method used to generate new variables and avoid spurious substitutions.
     */
    override fun refresh(scope: Context): Variable

    companion object {
        /**
         * Factory method for an [FluentBasedGoal] creation.
         */
        fun of(name: String): Variable = VariableImpl(name)
    }
}
