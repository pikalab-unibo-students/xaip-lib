/**
 * Exception thrown by [Fluent] whenever a mgu cannot be found.
 * @property first: first element of the unification.
 * @property second: second element of the unification.
 */
@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class NotUnifiableException

@JvmOverloads
constructor(val first: Any, val second: Any, cause: Throwable? = null) :
    Exception("Cannot unify $first with $second", cause)
