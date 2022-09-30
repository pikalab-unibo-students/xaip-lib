package impl

import Operator
import Plan

internal data class PlanImpl(override val actions: List<Operator>) : Plan
