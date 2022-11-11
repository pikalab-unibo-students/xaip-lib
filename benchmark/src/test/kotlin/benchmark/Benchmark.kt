package benchmark

import core.* // ktlint-disable no-wildcard-imports
import core.utility.then
import domain.BlockWorldDomain
import explanation.Question
import explanation.impl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec
import java.io.FileOutputStream
import java.io.OutputStream

interface Benchmark {
    fun write(filename: String?)
}

abstract class AbstractBenchmark(val problem: Problem, length: Int) : Benchmark {
    var plan: MutableList<Operator> = MutableList(length) {
        Operator.of(problem.domain.actions.first())
    }
    var resultsTime = mutableListOf<Long>()
    var resultsMemory = mutableListOf<Long>()
    fun addResult(question: Question, explanationType: String) {
        resultsTime.add(measureTimeMillis(question, explanationType))
        when (question) {
            is QuestionReplaceOperator -> resultsMemory.add(
                measureMemory(
                    QuestionReplaceOperator(
                        question.problem,
                        question.plan,
                        question.focus,
                        question.focusOn,
                        question.inState
                    ),
                    explanationType
                )
            )
            else -> resultsMemory.add(measureMemory(question, explanationType))
        }
    }
}
open class Benchmark1(
    problem: Problem,
    private val length: Int,
    private val flag: Int,
    private val explanationType: String = ""
) : AbstractBenchmark(problem, length) {
    lateinit var question: Question
    init {
        var i = 0
        while (i < length) {
            when (flag) {
                1 -> question = QuestionRemoveOperator(
                    problem,
                    Plan.of(plan),
                    Operator.of(problem.domain.actions.last())
                        .apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.a))
                )
                2 -> question = QuestionAddOperator(
                    problem,
                    Plan.of(plan),
                    Operator.of(problem.domain.actions.last()),
                    i
                )
                3 -> question = QuestionReplaceOperator(
                    problem,
                    Plan.of(plan),
                    Operator.of(problem.domain.actions.first())
                        .apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.a)),
                    i
                )
                4 -> question = QuestionPlanProposal(
                    problem,
                    Plan.of(plan),
                    Plan.of(MutableList(i) { Operator.of(problem.domain.actions.first()) })
                )
                5 -> question = QuestionPlanSatisfiability(
                    problem,
                    Plan.of(plan)
                )
            }
            addResult(question, explanationType)
            i++
        }
    }

    override fun write(filename: String?) {
        fun OutputStream.writeCsv(resultMemory: List<Long>, resultTime: List<Long>, flag: Int) {
            val writer = bufferedWriter()
            writer.write("""Domain, PlanLength, QuestionType, Index, Time, Memory""".trimMargin())
            writer.newLine()
            var i = 1
            resultTime.forEach {
                writer.write(
                    "${problem.domain.name}, ${plan.size}, $flag, " +
                        "$i, $it, ${resultMemory[resultTime.indexOf(it)]}"
                )
                writer.newLine()
                i++
            }
            writer.flush()
        }
        (
            (filename.equals("")).then(
                """res/resultQuestion${flag}Length${length}Explanation$explanationType.csv"""
            ) ?: "res/$filename"
            ).let {
            FileOutputStream(
                it
            )
                .apply { writeCsv(resultsMemory, resultsTime, flag) }
        }
    }
}

class Test : AnnotationSpec() {
    @Test
    fun prova() {
        var i = 1
        while (i <= 5) {
            Benchmark1(BlockWorldDomain.Problems.pickC, 200, i).write("")
            i++
        }
    }
}
