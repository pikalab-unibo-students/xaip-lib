import impl.VariableAssignmentImpl
import it.unibo.tuprolog.core.Substitution

interface VariableAssignment : Map<Variable, Value> {
    fun merge(other: VariableAssignment): VariableAssignment

    companion object {
        fun of(variable: Variable, value: Value): VariableAssignment = VariableAssignmentImpl(variable, value)

        fun empty(): VariableAssignment = VariableAssignmentImpl(Substitution.empty())
    }
}
