package benchmark
import core.Plan
import core.Planner
import core.State
import domain.BlockWorldDomain
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
import io.kotest.core.spec.style.AnnotationSpec
import java.lang.management.ManagementFactory

// ktlint-disable no-wildcard-imports

/*
 * Comparing performance of different question when asked to
 * perform similar operations.
 * */
open class ChangeSameOperator : AnnotationSpec() {
    val explainer = Explainer.of(BlockWorldDomain.Planners.stripsPlanner)
    private val problemStackAB = Problems.stackAB

    private val q1 = QuestionAddOperator(
        problemStackAB,
        Plan.of(listOf(pickA)),
        stackAB,
        1
    )

    private val q2 = QuestionRemoveOperator(
        problemStackAB,
        Plan.of(
            listOf(
                pickA,
                stackAB,
                stackAC
            )
        ),
        stackAC
    )

    private val q3 = QuestionReplaceOperator(
        problemStackAB,
        Plan.of(
            listOf(
                pickA,
                stackAC
            )
        ),
        stackAB,
        1,
        State.of(
            BlockWorldDomain.Fluents.atAArm,
            BlockWorldDomain.Fluents.clearA,
            BlockWorldDomain.Fluents.atCFloor,
            BlockWorldDomain.Fluents.clearC,
            BlockWorldDomain.Fluents.atDFloor,
            BlockWorldDomain.Fluents.clearD,
            BlockWorldDomain.Fluents.clearB,
            BlockWorldDomain.Fluents.atBFloor,
            BlockWorldDomain.Fluents.armEmpty
        )
    )

    fun addOperator(question: Question) {
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()
    }

    private fun measureTimeMillis(question: Question): Long {
        val start = System.currentTimeMillis()
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()
        return System.currentTimeMillis() - start
    }

    private fun measureMemory(question: Question): Long {
        val mbean = ManagementFactory.getMemoryMXBean()
        val beforeHeapMemoryUsage = mbean.heapMemoryUsage

        val instance = ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()

        val afterHeapMemoryUsage = mbean.heapMemoryUsage
        return afterHeapMemoryUsage.used - beforeHeapMemoryUsage.used
    }

    @Test
    fun test() {
        println(measureTimeMillis(q1))
        println(measureTimeMillis(q2))
        println(measureTimeMillis(q3))
    }

    @Test
    fun testMemory() {
        println(measureMemory(q1))
        println(measureMemory(q2))
        println(measureMemory(q3))
    }
}
