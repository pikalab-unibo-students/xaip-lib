package core.impl

import core.Operator
import core.Plan

internal data class PlanImpl(override val operators: List<Operator>) : Plan
