package benchmark
import core.Plan
import core.Planner
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.putdownA
import domain.BlockWorldDomain.Operators.putdownC
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackAC
import domain.BlockWorldDomain.Problems
import explanation.Explainer
import explanation.Question
import explanation.impl.ContrastiveExplanationPresenter
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec

/***
 * Comparing performance of different question (Q1-Q2( when asked to
 * perform similar operations: adding/replacing an operator in
 * different positions.
 * */

class ModifyElementInDifferentPositions : AnnotationSpec() {
    private val problemPickC = Problems.pickC
    private val problemStackAB = Problems.stackAB

    private val q1length1 = QuestionAddOperator(
        problemPickC,
        Plan.of(emptyList()),
        pickC,
        0
    )

    private val q1length2 = QuestionAddOperator(
        problemStackAB,
        Plan.of(listOf(pickA)),
        stackAB,
        1
    )

    private val q1length3 = QuestionAddOperator(
        problemPickC,
        Plan.of(listOf(pickA, putdownA)),
        pickC,
        2
    )

    private val q1length4 = QuestionAddOperator(
        problemStackAB,
        Plan.of(listOf(pickC, putdownC, pickA)),
        stackAB,
        1
    )

    private val q3length1 = QuestionReplaceOperator(
        problemPickC,
        Plan.of(
            listOf(
                pickA
            )
        ),
        pickC,
        0
    )

    private val q3length2 = QuestionReplaceOperator(
        problemStackAB,
        Plan.of(
            listOf(
                pickA,
                stackAC
            )
        ),
        stackAB,
        1
    )

    private val q3length3 = QuestionReplaceOperator(
        problemPickC,
        Plan.of(
            listOf(
                pickA,
                putdownA,
                pickA
            )
        ),
        pickC,
        2
    )

    private val q3length4 = QuestionReplaceOperator(
        problemStackAB,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAC
            )
        ),
        stackAB,
        3
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
        println(measureTimeMillis(q1length1))
        println(measureTimeMillis(q1length2))
        println(measureTimeMillis(q1length3))
        println(measureTimeMillis(q1length4))
        println(measureTimeMillis(q3length1))
        println(measureTimeMillis(q3length2))
        println(measureTimeMillis(q3length3))
        println(measureTimeMillis(q3length4))
    }
}
