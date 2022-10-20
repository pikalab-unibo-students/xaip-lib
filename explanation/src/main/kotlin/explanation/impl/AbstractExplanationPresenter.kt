package explanation.impl

import explanation.Explanation
import explanation.ExplanationPresenter
import kotlin.math.abs

open class AbstractExplanationPresenter protected constructor(
    override val explanation: Explanation
) : ExplanationPresenter {
    val additionalOperators = abs(explanation.novelPlan.operators.size - explanation.minimalSolutionLength())
    private val isProposedPlanMinimalSolution =
        explanation.minimalSolutionLength() == explanation.novelPlan.operators.size
    private val problemHasSolution by lazy {
        if (explanation.isProblemSolvable()) {
            "The problem is solvable.\n"
        } else {
            "The problem is not solvable.\n"
        }
    }
    private val isPlanMinimalSolution by lazy {
        if (!isProposedPlanMinimalSolution) {
            "The problem is not a minimal solution; you can remove:$additionalOperators operators\n"
        } else {
            "The proposed plan is a minimal solution.\n"
        }
    }

    private val isPlanLengthAcceptable by lazy {
        if (!explanation.isPlanLengthAcceptable()) {
            "The length of the plan is unacceptable; $additionalOperators are missing."
        } else {
            "The length of the plan is acceptable;" +
                " there are $additionalOperators operators respect the minimal solution."
        }
    }

    private val areRequiredOperatorsPresent by lazy {
        if (explanation.areIdempotentOperatorsPresent().isNotEmpty()) {
            var str = ""
            explanation.areIdempotentOperatorsPresent().map {
                str = str.plus(
                    "${it.value.operator2?.name} invalidates ${it.key.name}'s effects" +
                        "you should remove ${it.value.occurence2 - it.value.occurence1 + 1 } " +
                        "instances of ${it.value.operator2?.name}\n"
                )
            }
            str
        } else {
            ""
        }
    }

    private val isPlanValid by lazy {
        if (explanation.isPlanValid()) {
            "The proposed plan is valid.\n"
        } else {
            "The proposed plan is not a valid solution for the problem.\n"
        }
    }

    override fun present(): String {
        var finalString = problemHasSolution
        if (explanation.isProblemSolvable()) {
            finalString = finalString.plus(isPlanValid)
            if (explanation.isPlanValid()) {
                finalString = finalString.plus(isPlanMinimalSolution)
            } else {
                finalString = finalString.plus(isPlanLengthAcceptable)
                if (explanation.isPlanLengthAcceptable()) {
                    finalString = finalString.plus(areRequiredOperatorsPresent)
                }
            }
        }
        return finalString
    }
}
