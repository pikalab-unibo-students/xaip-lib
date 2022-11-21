import core.Operator
import core.Plan
import core.Problem
import core.utility.then
import domain.BlockWorldDomain
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.putdownA
import domain.BlockWorldDomain.Operators.putdownB
import domain.BlockWorldDomain.Operators.putdownC
import domain.BlockWorldDomain.Operators.putdownD
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackAC
import domain.BlockWorldDomain.Operators.stackAD
import domain.BlockWorldDomain.Operators.stackBA
import domain.BlockWorldDomain.Operators.stackBC
import domain.BlockWorldDomain.Operators.stackBD
import domain.BlockWorldDomain.Operators.stackCA
import domain.BlockWorldDomain.Operators.stackCB
import domain.BlockWorldDomain.Operators.stackCD
import domain.BlockWorldDomain.Operators.stackDA
import domain.BlockWorldDomain.Operators.stackDB
import domain.BlockWorldDomain.Operators.stackDC
import domain.BlockWorldDomain.Operators.unstackAB
import domain.BlockWorldDomain.Operators.unstackAC
import domain.BlockWorldDomain.Operators.unstackAD
import domain.BlockWorldDomain.Operators.unstackBA
import domain.BlockWorldDomain.Operators.unstackBC
import domain.BlockWorldDomain.Operators.unstackBD
import domain.BlockWorldDomain.Operators.unstackCA
import domain.BlockWorldDomain.Operators.unstackCB
import domain.BlockWorldDomain.Operators.unstackCD
import domain.BlockWorldDomain.Operators.unstackDA
import domain.BlockWorldDomain.Operators.unstackDB
import explanation.Question
import explanation.impl.* // ktlint-disable no-wildcard-imports
import java.io.FileOutputStream
import java.io.OutputStream
import domain.BlockWorldDomain.Operators as bwOperators
import domain.LogisticDomain.Operators as lOperators

open class BaseBenchmark(
    problem: Problem,
    private val length: Int,
    private val flag: Int,
    private val explanationType: String = ""
) : AbstractBenchmark(problem, length) {
    lateinit var question: Question
    val idempotentActionSetBlockWorld by lazy {
        listOf(
            listOf(pickA, putdownA),
            listOf(pickB, putdownB),
            listOf(pickC, putdownC),
            listOf(pickD, putdownD),
            listOf(pickA, stackAB, unstackAB),
            listOf(pickA, stackAC, unstackAC),
            listOf(pickA, stackAD, unstackAD),
            listOf(pickB, stackBA, unstackBA),
            listOf(pickB, stackBC, unstackBC),
            listOf(pickB, stackBD, unstackBD),
            listOf(pickC, stackCA, unstackCA),
            listOf(pickC, stackCB, unstackCB),
            listOf(pickC, stackCD, unstackCD),
            listOf(pickD, stackDA, unstackDA),
            listOf(pickD, stackDB, unstackDB),
            listOf(pickD, stackDC, bwOperators.unstackDC)
        )
    }
    private val operator by lazy {
        when (problem.domain.name) {
            "block_world" -> BlockWorldDomain.Operators.pickA
            "logistic_world" -> (flag == 1).then(lOperators.moveRfromL1toL3) ?: lOperators.moveRfromL1toL2
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

    fun createPlan(problem: Problem, maxLength: Int): MutableList<MutableList<Operator>> {
        val plans = mutableListOf(mutableListOf(pickA, putdownA))
        for (plan in plans) {
            if (problem.domain.name == "bockWorld_domain") {
                for (seq in idempotentActionSetBlockWorld) {
                    val list = mutableListOf<Operator>()
                    for (elem in seq) { list.add(elem) }
                    plans.add(list)
                }
                if (plans.last().size > maxLength) break
            } else { } // Logistic domain
        }
        return plans
    }
}
