package impl

import it.unibo.tuprolog.core.Scope

/**
 * Wrapper for [Scope].
 */
interface Context {
    companion object {
        /**
         * Factory method to create an empty [Context].
         */
        fun empty(): Context = ContextImpl(Scope.empty())

        /**
         * Factory method to create a [Context].
         */
        fun of(vararg vars: String) = ContextImpl(Scope.of(*vars))
    }
}
