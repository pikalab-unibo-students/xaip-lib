interface VariableAssignment : Map<Variable, Value> {
    fun merge(other: VariableAssignment): VariableAssignment
}