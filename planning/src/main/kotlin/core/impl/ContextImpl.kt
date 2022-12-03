package core.impl

import it.unibo.tuprolog.core.Scope

/**
 * Entity for the [Context] interface.
 */
class ContextImpl(internal val delegate: Scope) : Context
