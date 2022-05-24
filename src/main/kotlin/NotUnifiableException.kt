@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class NotUnifiableException
    @JvmOverloads
    constructor(val first: Any, val second: Any, cause: Throwable? = null)
    : Exception("Cannot unify $first with $second", cause)