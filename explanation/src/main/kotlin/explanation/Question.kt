package explanation

import core.Domain
import core.Operator
import core.Plan
import core.Problem

/**
 * An [Question] represents a way for a user to express question about a plan.
 *
 * @property problem: is an instance of [Problem].
 * @property plan: is the original the user wants to challenge.
 * @property focus: is the [Operator] the user wants to change either by adding, or removing it from the plan).
 * @property focusOn: is the index of the [Operator] the user wants to remove from the plan.
 * */
interface Question {
    /**
     *
     */
    val problem: Problem

    /**
     *
     */
    val plan: Plan

    /**
     *
     */
    val focus: Operator

    /**
     *
     */
    val focusOn: Int

    /**
     * Method to create a new [Domain] according to the [Question] the user is asking.
     */
    fun buildHypotheticalDomain(): Domain

    /**
     * Method to create a new [Problem] according to the [Question] the user is asking.
     */
    fun buildHypotheticalProblem(): Sequence<Problem>
}
