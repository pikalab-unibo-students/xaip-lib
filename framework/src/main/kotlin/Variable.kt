import impl.VariableImpl
import it.unibo.tuprolog.core.Scope
/**
 * Entity responsible for the parameterisation of an [Action].
 * Variables that stand for terms of the [Problem] instance; they are instantiated to [Object] from
 * a specific problem instance when an [Action] is grounded for application.
 *
 * @property name: name of the [Variable].
 */
interface Variable : Value {
    val name: String

    /**
     * Method used to generate new variables and avoid spurious substitutions.
     */
    override fun refresh(scope: Scope): Variable

    companion object {
        /**
         * Factory method for an [FluentBasedGoal] creation.
         */
        fun of(name: String): Variable = VariableImpl(name)
    }
}
