package explanation

import Operator
import Plan

interface Explanation {
    val originalPlan: Plan
    val novelPlan: Plan

    val addList: List<Operator>
    val deleteList: List<Operator>
    val existingList: List<Operator>
}
