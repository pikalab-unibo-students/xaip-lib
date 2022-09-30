package explanation.impl

import Operator
import Plan
import explanation.Explanation

/**
 *
 */
data class ExplanationImpl(override val originalPlan: Plan, override val novelPlan: Plan) : Explanation {
    override val addList: List<Operator> by lazy {
        this.novelPlan.actions.filter {
            !this.originalPlan.actions.contains(it)
        }
    }
    override val deleteList: List<Operator> by lazy {
        this.originalPlan.actions.filter {
            !this.novelPlan.actions.contains(it)
        }
    }
    override val existingList: List<Operator> by lazy {
        this.originalPlan.actions.filter {
            this.novelPlan.actions.contains(it)
        }
    }

    override fun toString(): String =
        """${ExplanationImpl::class.simpleName}(
            |  ${ExplanationImpl::originalPlan.name}=${this.originalPlan},
            |  ${ExplanationImpl::novelPlan.name}=${this.novelPlan},
            |  - Diff(original plan VS new plan):
            |  ${ExplanationImpl::addList.name}=$addList,
            |  ${ExplanationImpl::deleteList.name}=$deleteList,
            |  ${ExplanationImpl::existingList.name}=$existingList
            |)
        """.trimMargin()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExplanationImpl

        if (this.originalPlan != other.originalPlan) return false
        if (this.novelPlan != other.novelPlan) return false

        return true
    }

    override fun hashCode(): Int {
        var result = this.originalPlan.hashCode()
        result = 31 * result + this.novelPlan.hashCode()
        return result
    }
}
