interface Applicable<Self : Applicable<Self>> {
    fun apply(substitution: VariableAssignment): Self
}