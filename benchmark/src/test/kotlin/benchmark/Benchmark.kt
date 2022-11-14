package benchmark
import core.* // ktlint-disable no-wildcard-imports
import core.utility.then
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Problems.pickC
import domain.LogisticDomain.Operators.moveRfromL1toL2
import domain.LogisticDomain.Operators.moveRfromL1toL3
import domain.LogisticDomain.Problems.robotFromLoc1ToLoc2
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
        when (problem.domain.name) {
            "block_world" -> Operator.of(problem.domain.actions.first())
            "logistic_world" -> moveRfromL1toL2
            else -> throw NoSuchElementException("Domain ${problem.domain.name} is not supported")
        }
    }
    val resultsTime by lazy { mutableListOf<Long>() }
    val resultsMemory by lazy { mutableListOf<Long>() }

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
    private val operator by lazy {
        when (problem.domain.name) {
            "block_world" -> pickA
            "logistic_world" -> (flag == 1).then(moveRfromL1toL3) ?: moveRfromL1toL2
            else -> throw NoSuchElementException("Domain ${problem.domain.name} is not supported")
        }
    }
    init {
        var i = 0
        while (i < length) {
            when (flag) {
                1 -> question = QuestionRemoveOperator(
                    problem,
                    Plan.of(plan),
                    operator
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
                    operator,
                    i
                )
                4 -> question = QuestionPlanProposal(
                    problem,
                    Plan.of(plan),
                    Plan.of(
                        MutableList(i) {
                            operator
                        }
                    )
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
                """res/${problem.domain.name}/Question${flag}Length${length}Explanation$explanationType.csv"""
            ) ?: "res/$filename"
            ).let {
            FileOutputStream(
                it
            ).apply { writeCsv(resultsMemory, resultsTime, flag) }
        }
    }
}

class Test : AnnotationSpec() {
    private val problems = listOf(pickC, robotFromLoc1ToLoc2)
    @Test
    fun buildBenchmark() {
        for (problem in problems)
            for (i in 1..5)
                for (j in 50..200 step 50)
                    Benchmark1(problem, j, i).write("")
    }
}
