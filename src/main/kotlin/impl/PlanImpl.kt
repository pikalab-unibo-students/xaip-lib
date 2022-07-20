package impl

import Action
import Plan

internal data class PlanImpl(override val actions: List<Action>) : Plan