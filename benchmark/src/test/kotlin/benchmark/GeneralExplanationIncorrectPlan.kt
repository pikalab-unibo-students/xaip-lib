package benchmark

import core.Plan
import core.Planner
import domain.BlockWorldDomain
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.putdownC
import domain.BlockWorldDomain.Operators.stackAC
import domain.BlockWorldDomain.Operators.stackAD
import explanation.Explainer
import explanation.Question
import explanation.impl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec

class GeneralExplanationIncorrectPlan : AnnotationSpec() {
    private val problemStackAB = BlockWorldDomain.Problems.stackAB

    private val q1 = QuestionAddOperator(
        problemStackAB,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA
            )
        ),
        stackAC,
        2
    )
    private val q2 = QuestionRemoveOperator(
        problemStackAB,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAC,
                pickD
            )
        ),
        pickD
    )

    private val q3 = QuestionReplaceOperator(
        problemStackAB,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAC
            )
        ),
        stackAD,
        3
    )

    private val q4 = QuestionPlanProposal(
        problemStackAB,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAC
            )
        ),
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAD
            )
        )
    )

    private val q5 = QuestionPlanSatisfiability(
        problemStackAB,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAD
            )
        )
    )

    private fun measureTimeMillis(question: Question): Long {
        val start = System.currentTimeMillis()
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()
        return System.currentTimeMillis() - start
    }

    @Test
    fun test() {
        println(measureTimeMillis(q1))
        println(measureTimeMillis(q2))
        println(measureTimeMillis(q3))
        println(measureTimeMillis(q4))
        println(measureTimeMillis(q5))
    }
}
