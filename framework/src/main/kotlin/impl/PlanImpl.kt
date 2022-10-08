package impl

import Operator
import Plan

internal data class PlanImpl(override val operators: List<Operator>) : Plan
