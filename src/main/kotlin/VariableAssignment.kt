import impl.VariableAssignmentImpl

interface VariableAssignment : Map<Variable, Value> {
    fun merge(other: VariableAssignment): VariableAssignment
    companion object {
        fun of(variable: Variable, value: Value): VariableAssignment =
            VariableAssignmentImpl(variable, value)
    }
}