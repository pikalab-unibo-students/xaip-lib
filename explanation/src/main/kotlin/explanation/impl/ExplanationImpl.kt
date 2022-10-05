package explanation.impl

import FluentBasedGoal
import NotUnifiableException
import Operator
import Plan
import State
import explanation.Explanation
import explanation.Question
import explanation.Simulator
import it.unibo.tuprolog.core.Substitution

/**
 *
 */
data class ExplanationImpl(
    override val originalPlan: Plan,
    override var novelPlan: Plan,
    override val question: Question
    ) : Explanation {
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
     private val simulator = Simulator.of()

    init{
        // aggiornamento del piano in caso si tratti di Q3
        if(question is Question3){
            val actionToKeep = question.plan.actions.subList(
                0,
                question.focusOn
            ).toMutableList()
            novelPlan = Plan.of(actionToKeep.also { it.add(question.focus) }.also { it.addAll(novelPlan.actions) })
        }
    }

    private fun finalStateComplaintWithGoal(goal: FluentBasedGoal, currentState: State): Boolean {
        var indice = 0
        for (fluent in goal.targets) {
            if (!fluent.isGround) {
                for (fluentState in currentState.fluents) {
                    val tmp = try {
                        fluentState.mostGeneralUnifier(fluent)
                    } catch (_: NotUnifiableException) {
                        null
                    }
                    // TODO(Se questa roba è sensata va fixata anche nell'altro modulo)
                    if (tmp != Substitution.empty() && tmp != Substitution.empty() && tmp != null) {
                        indice++
                        break
                    }
                }
            } else {
                if (currentState.fluents.contains(fluent)) indice++ else indice--
            }
        }
        return goal.targets.size == indice
    }

    override fun isValid(): Boolean {
        val states = simulator.simulate(novelPlan, question.problem.initialState)
        var flag = false
        if(states.isNotEmpty()){
            for(state in states) {
                val tmp = finalStateComplaintWithGoal(question.problem.goal as FluentBasedGoal, state)
                if(tmp) flag = true
            }

        }
        return flag
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
