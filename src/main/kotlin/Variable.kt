import impl.VariableImpl
import it.unibo.tuprolog.core.Scope
/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface Variable : Value {
    val name: String
    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    override fun refresh(scope: Scope): Variable

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(name: String): Variable = VariableImpl(name)
    }
}
