import it.unibo.tuprolog.core.Scope

interface Applicable<Self : Applicable<Self>> {
    fun apply(substitution: VariableAssignment): Self
    fun refresh(scope: Scope = Scope.empty()): Self
}