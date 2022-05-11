package impl

import Action
import Fluent
import State

class StateImpl(override val fluents: Set<Fluent>) : State {
    override fun apply(action: Action): State {
        TODO("Not yet implemented")
    }

    override fun isApplicable(action: Action): Boolean {
        TODO("Not yet implemented")
    }
}