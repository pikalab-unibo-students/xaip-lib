import impl.VariableAssignmentImpl
import it.unibo.tuprolog.core.Substitution
/***
 * Entity responsible for performing logic substations.
 */
interface VariableAssignment : Map<Variable, Value> {

    /***
     * Method resposible of performing the unification among two logic substitutions.
     */
    fun merge(other: VariableAssignment): VariableAssignment

    companion object {
        /***
         * Factory method for an [VariableAssignment] creation.
         */
        fun of(variable: Variable, value: Value): VariableAssignment = VariableAssignmentImpl(variable, value)

        /***
         * Method that wraps an empty logic substation.
         */
        fun empty(): VariableAssignment = VariableAssignmentImpl(Substitution.empty())
    }
}
