import impl.VariableAssignmentImpl
import it.unibo.tuprolog.core.Substitution
/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface VariableAssignment : Map<Variable, Value> { /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun merge(other: VariableAssignment): VariableAssignment

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(variable: Variable, value: Value): VariableAssignment = VariableAssignmentImpl(variable, value)

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun empty(): VariableAssignment = VariableAssignmentImpl(Substitution.empty())
    }
}
