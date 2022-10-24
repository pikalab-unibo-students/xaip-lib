package explanation.impl

import Fluent
import FluentBasedGoal
import explanation.Explanation
import explanation.ExplanationPresenter
import impl.res.FrameworkUtilities.then
import java.lang.Math.abs

/**
 * Assunzione di base i goal DEVONO essere GROUND.
 * Assunzione 2 implicitamente gestiamo la spiegazioni come
 * se il piano di lunghezza minima fosse la soluzione ottima?
 * Perché banalmente perché non abbiamo altre metriche
 * per testare l'ottimalità del piano.
 *
 * Note: inizialmente questa era pensata come una classe pseudo abstract
 * non istanziabile, ma in realtà non abbiamo due tipi distinti di explanation
 * le contrastive sono sottotipi del tipo di base.
 * Quindi mi sa che sia più sensato rendere questa la classe base e bona.
 *
 */
/*
open class AbstractExplanationPresenter protected constructor(
    override val explanation: Explanation
) : ExplanationPresenter {
    */
open class BaseExplanationPresenter(
    final override val explanation: Explanation
) : ExplanationPresenter {
    private val minimalSolution by lazy {
        explanation.explainer.minimalPlanSelector()
    }

    /**
     * Numero di operatori aggiuntivi rispetto al piano di lunghezza minima
     */
    private val additionalOperators = abs(explanation.novelPlan.operators.size - explanation.minimalSolutionLength())

    private val operatorsMissing by lazy {
        explanation.explainer.minimalPlanSelector().operators
            .filter { !explanation.novelPlan.operators.contains(it) }
    }

    private val isProposedPlanMinimalPlan = ((additionalOperators == 0) then true) ?: false

    private val isProblemSolvable by lazy {
        if (explanation.isProblemSolvable()) {
            "The problem ${(explanation.question.problem.goal as FluentBasedGoal).targets} is solvable.\n"
        } else {
            "The problem is not solvable.\n"
        }
    }

    private val minimalPlan by lazy {
        "The minimal solution is: ${minimalSolution.operators}\n"
    }

    private val isPlanMinimalSolution by lazy {
        if (!isProposedPlanMinimalPlan) {
            "The plan is not the minimal solution; you can remove: $additionalOperators operators."
        } else {
            "The plan is the minimal solution.\n"
        }
    }

    private val isPlanLengthAcceptable by lazy {
        if (!explanation.isPlanLengthAcceptable()) {
            "The length of the plan is unacceptable; $additionalOperators operator missing: $operatorsMissing.\n"
        } else {
            "The length of the plan is acceptable;" +
                " there are $additionalOperators additional operators respect the minimal solution.\n"
        }
    }

    private val `are the idempotent operators of the required operators present` by lazy {
        if (explanation.areIdempotentOperatorsPresent().isNotEmpty()) {
            var str = ""
            explanation.areIdempotentOperatorsPresent().map {
                str = str.plus(
                    "${it.value.operator2?.name} invalidates ${it.key.name}'s effects." +
                        "You should remove ${it.value.occurence2 - it.value.occurence1 + 1 } " +
                        "instances of ${it.value.operator2?.name}\n"
                )
            }
            str
        } else {
            ""
        }
    }

    private val planContainsRequiredOperators by lazy {
        if (explanation.novelPlan.operators.containsAll(minimalSolution.operators)) {
            "The plan contains all the necessary operators.\n"
        } else {
            "ERROR: the plan does not contains all the necessary operators.\n"
        }
    }

    private val isPlanValid by lazy {
        if (explanation.isPlanValid()) {
            "The plan ${explanation.novelPlan.operators} is a valid solution for the problem.\n"
        } else {
            "The plan ${explanation.novelPlan.operators} is not a valid solution for the problem.\n"
        }
    }

    private fun areAllGroundFluents(targets: Set<Fluent>) = targets.all { it.isGround }
    override fun present(): String {
        if (!areAllGroundFluents((explanation.question.problem.goal as FluentBasedGoal).targets)) {
            throw IllegalArgumentException("Goal must contain only ground fluents")
        }
        var finalString = isProblemSolvable
        if (explanation.isProblemSolvable()) {
            finalString = finalString.plus(isPlanValid)
            finalString = finalString.plus(minimalPlan)
            if (explanation.isPlanValid()) {
                finalString = finalString.plus(isPlanMinimalSolution)
                if (additionalOperators != 0) finalString = finalString.plus(planContainsRequiredOperators)
            } else {
                finalString = finalString.plus(isPlanLengthAcceptable)
                finalString = finalString.plus(planContainsRequiredOperators)
                if (explanation.isPlanLengthAcceptable()) {
                    finalString = finalString.plus(`are the idempotent operators of the required operators present`)
                }
            }
        }
        return finalString
    }
}
