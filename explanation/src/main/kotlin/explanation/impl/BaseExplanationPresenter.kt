package explanation.impl

import core.Fluent
import core.FluentBasedGoal
import core.utility.then
import explanation.Explanation
import explanation.ExplanationPresenter

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
internal open class BaseExplanationPresenter(
    override val explanation: Explanation
) : ExplanationPresenter {
    private val minimalSolution by lazy { explanation.explainer.minimalPlanSelector(explanation.question.problem) }

    private val beVerb by lazy { (additionalOperators.size > 1).then("are") ?: "is" }

    private val operator by lazy { (additionalOperators.size > 1).then("operators") ?: "operator" }

    private val isValid by lazy { explanation.isPlanValid().then("not") ?: "" }

    private val isSolvable by lazy { explanation.isProblemSolvable().then("not ") ?: "" }

    private val isMinimal by lazy { (!isProposedPlanMinimalPlan).then("not ") ?: "" }

    private val additionalOperators by lazy {
        explanation.novelPlan.operators.filter { !minimalSolution.operators.contains(it) }
    }

    private val operatorsMissing by lazy {
        minimalSolution.operators.filter { !explanation.novelPlan.operators.contains(it) }
    }

    private val isProposedPlanMinimalPlan = ((additionalOperators.isEmpty()) then true) ?: false

    internal val isProblemSolvable by lazy {
        "The problem ${(explanation.question.problem.goal as FluentBasedGoal).targets} is ${isSolvable}solvable."
    }

    private val minimalPlan by lazy { "The minimal solution is: ${minimalSolution.operators}\n" }

    private val isPlanMinimalSolution by lazy { "The plan is ${isMinimal}the minimal solution" }

    private val areThereAdditionalOperators by lazy {
        (additionalOperators.isNotEmpty()).then(
            "\nThere $beVerb ${additionalOperators.size} additional $operator " +
                "respect the minimal solution: $additionalOperators.\n"
        ) ?: ""
    }

    private val planContainsRequiredOperators by lazy {
        (!explanation.novelPlan.operators.containsAll(minimalSolution.operators)).then(
            ", the plan does not contains all the necessary operators.\n" +
                "There $beVerb ${additionalOperators.size} $operator missing: $operatorsMissing.\n"
        ) ?: "."
    }

    private val isPlanValid by lazy {
        "The plan ${explanation.novelPlan.operators} is ${isValid}a valid solution for the problem.\n"
    }

    private fun areAllGroundFluents(targets: Set<Fluent>) = targets.all { it.isGround }

    override fun present(): String {
        if (!areAllGroundFluents((explanation.question.problem.goal as FluentBasedGoal).targets)) {
            throw IllegalArgumentException("Goal must contain only ground fluents")
        }
        var finalString = isProblemSolvable
        finalString.plus("\n")
        if (explanation.isProblemSolvable()) {
            finalString = finalString.plus(isPlanValid)
            finalString = finalString.plus(minimalPlan)

            finalString = finalString.plus(isPlanMinimalSolution)
            finalString = finalString.plus(planContainsRequiredOperators)
            finalString = finalString.plus(areThereAdditionalOperators)
        }
        return finalString
    }

    /**
     *
     */
    override fun presentMinimalExplanation(): String {
        return """Minimal explanation:
            | $isProblemSolvable
            | the original plan was: ${explanation.originalPlan.operators},
            | the plan: ${explanation.novelPlan.operators} is valid: ${explanation.isPlanValid()}
            | plan length acceptable: ${explanation.isPlanLengthAcceptable()}
            | operators missing: $operatorsMissing
            | additional missing: $additionalOperators
            |
            
        """.trimMargin()
    }
}
