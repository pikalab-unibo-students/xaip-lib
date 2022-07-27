import it.unibo.tuprolog.core.Scope
/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface Applicable<Self : Applicable<Self>> {
    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun apply(substitution: VariableAssignment): Self

    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun refresh(scope: Scope = Scope.empty()): Self
}
