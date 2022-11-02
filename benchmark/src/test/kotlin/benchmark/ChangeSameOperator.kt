package benchmark
import core.Plan
import core.Planner
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackAC
import domain.BlockWorldDomain.Problems
import explanation.Explainer
import explanation.Question
import explanation.impl.ContrastiveExplanationPresenter
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionRemoveOperator
import explanation.impl.QuestionReplaceOperator
import org.openjdk.jmh.annotations.Benchmark

// ktlint-disable no-wildcard-imports

/*
 * Comparing performance of different question when asked to
 * perform similar operations.
 * */
open class ChangeSameOperator {
    fun init() {
        val problem = Problems.stackAB

        val q1 = QuestionAddOperator(
            problem,
            Plan.of(listOf(pickA)),
            stackAB,
            1
        )

        val q2 = QuestionRemoveOperator(
            problem,
            Plan.of(
                listOf(
                    pickA,
                    stackAB,
                    stackAC
                )
            ),
            stackAC
        )

        val q3 = QuestionReplaceOperator(
            problem,
            Plan.of(
                listOf(
                    pickA,
                    stackAC
                )
            ),
            stackAB,
            1
        )

        addOperator(q1)
        addOperator(q2)
        addOperator(q3)
    }

    @Benchmark
    fun addOperator(question: Question) {
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips(), question).explain()
        ).presentContrastiveExplanation()
    }
}
