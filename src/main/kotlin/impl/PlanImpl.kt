package impl

import Action
import Plan

internal data class PlanImpl(override var actions: List<Action>) : Plan