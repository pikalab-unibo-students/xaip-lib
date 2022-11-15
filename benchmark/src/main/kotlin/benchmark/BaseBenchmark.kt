package benchmark

import core.Operator
import core.Plan
import core.Problem
import core.utility.then
import domain.BlockWorldDomain
import domain.LogisticDomain.Operators
import explanation.Question
import explanation.impl.* // ktlint-disable no-wildcard-imports
import java.io.FileOutputStream
import java.io.OutputStream

open class BaseBenchmark(
    problem: Problem,
    private val length: Int,
    private val flag: Int,
    private val explanationType: String = ""
) : AbstractBenchmark(problem, length) {
    lateinit var question: Question
    private val operator by lazy {
        when (problem.domain.name) {
            "block_world" -> BlockWorldDomain.Operators.pickA
            "logistic_world" -> (flag == 1).then(Operators.moveRfromL1toL3) ?: Operators.moveRfromL1toL2
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
                """res/benchmark/${problem.domain.name}/Question${flag}Length${length}Explanation$explanationType.csv"""
            ) ?: "res/$filename"
            ).let {
            FileOutputStream(
                it
            ).apply { writeCsv(resultsMemory, resultsTime, flag) }
        }
    }
}
