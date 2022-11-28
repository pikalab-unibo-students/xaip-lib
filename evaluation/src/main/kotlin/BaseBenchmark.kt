import core.Plan
import core.Planner
import core.Problem
import core.utility.then
import domain.BlockWorldDomain.Actions
import domain.BlockWorldDomain.Operators.pickA
import domain.LogisticDomain.Operators.moveRfromL1toL2
import explanation.Explainer
import explanation.Question
import explanation.impl.* // ktlint-disable no-wildcard-imports
import explanation.utils.retrieveAction
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.random.Random
import domain.LogisticDomain.Actions as Lactions

open class BaseBenchmark {
    private val explainer by lazy { Explainer.of(Planner.strips()) }

    private val blockWorldName by lazy { "block_world" }
    private val logisticName by lazy { "logistic_world" }

    private val resultsTime by lazy { mutableMapOf<Plan, Long>() }
    private val resultsMemory by lazy { mutableMapOf<Plan, Long>() }
    private fun problemFolder(name: String) = (name == blockWorldName).then(blockWorldName) ?: logisticName
    private fun explanationFolder(name: String) = (name.startsWith("c", true))
        .then("contrastiveExplanation") ?: "generalExplanation"

    private fun osFolder(name: String) = (name.startsWith("l", true)).then("linux") ?: "windows"

    /**
     * Method responsible for writing the benchmarks.
     */
    fun writeBenchmark(
        filename: String,
        problem: Problem,
        explanationType: String,
        questionType: Int,
        plans: List<Plan>,
        isWorkFlow: Boolean = false
    ) {
        init(plans.toMutableList(), questionType, problem, explanationType)
        write(filename, problem, explanationType, questionType, isWorkFlow)
    }
    private fun write(
        filename: String,
        problem: Problem,
        explanationType: String,
        questionType: Int,
        isWorkFlow: Boolean = false
    ) {
        fun OutputStream.writeCsv() {
            val writer = bufferedWriter()
            writer.write("""Domain, PlanLength, QuestionType, Time, Memory""".trimMargin())
            writer.newLine()
            var i = 1
            resultsTime.forEach {
                writer.write(
                    "${problem.domain.name}, ${it.key.operators.size}, $questionType, " +
                        "${it.value}, ${resultsMemory[it.key]}"
                )
                writer.newLine()
                i++
            }
            writer.flush()
        }
        val prefix = isWorkFlow.then("") ?: "evaluation/"
        (
            (filename == "").then(
                """${prefix}res/benchmark/${problemFolder(problem.domain.name)}/
                    ${osFolder(System.getProperty("os.name"))}
                    /${explanationFolder(explanationType)}/Question${questionType}Explanation$explanationType.csv
                """.replace("\\s".toRegex(), "")
            ) ?: "res/$filename"
            ).let {
            FileOutputStream(
                it
            ).apply { writeCsv() }
        }
    }

    private fun addResult(question: Question, explanationType: String) {
        val memoryOccupation = measureMemory2(question, explanationType)
        if (memoryOccupation > 0L) {
            resultsMemory[question.plan] = memoryOccupation
            when (question) {
                is QuestionReplaceOperator -> resultsTime[question.plan] = measureTimeMillis(
                    QuestionReplaceOperator(
                        question.problem,
                        question.plan,
                        question.focus,
                        question.focusOn,
                        question.inState
                    ),
                    explanationType
                )

                else -> resultsTime[question.plan] = measureTimeMillis(question, explanationType)
            }
        }
    }
    private fun init(plans: MutableList<Plan>, question: Int, problem: Problem, explanationType: String = "") {
        for (plan in plans) {
            when (question) {
                1 -> explainQuestion1(plan, problem, explanationType)
                2 -> explainQuestion2(plan, problem, explanationType)
                3 -> explainQuestion3(plan, problem, explanationType)
                4 -> explainQuestion4(plan, problem, explanationType)
                5 -> explainQuestion5(plan, problem, explanationType)
                else -> error("Question not supported")
            }
        }
    }

    private fun explainQuestion1(plan: Plan, problem: Problem, type: String) {
        val actions = setOf(Actions.pick, Actions.stack, Lactions.move)
        for (operator in plan.operators)
            if (operator !in explainer.minimalPlanSelector(problem).operators
                && problem.domain.actions.retrieveAction(operator) in actions
            ) {
                println("1 $plan \n $operator")
                addResult(QuestionRemoveOperator(problem, plan, operator), type)
            }
    }

    private fun explainQuestion2(plan: Plan, problem: Problem, type: String) {
        for (i in 1..plan.operators.size) {
            val operator = plan.operators.shuffled(Random(i)).first()
            println("2 $plan \n $operator")
            addResult(QuestionAddOperator(problem, plan, operator, i), type)
        }
    }
    private fun explainQuestion3(plan: Plan, problem: Problem, type: String) {
        val actions = setOf(Actions.putdown, Actions.unstack, Lactions.unload)
        val op = (problem.domain.name == "block_world").then(pickA) ?: moveRfromL1toL2
        val iterator = plan.operators.iterator()
        var i = 0
        for (operator in iterator) {
            if (problem.domain.actions.retrieveAction(operator) in actions &&
                plan.operators.last() != operator) {
                println("3 $plan \n $operator")
                try {
                    addResult(QuestionReplaceOperator(problem, plan, op, i + 1), type)
                }catch (_: Exception){}
            }
            i++
        }
    }

    private fun explainQuestion4(plan: Plan, problem: Problem, type: String) {
        val plans = mutableSetOf<Plan>()
        for (i in 1..plan.operators.size / 2) {
            plans.add(Plan.of(plan.operators.shuffled(Random(i))))
        }
        for (p in plans) {
            addResult(QuestionPlanProposal(problem, plan, p), type)
        }
    }

    private fun explainQuestion5(plan: Plan, problem: Problem, type: String) {
        addResult(QuestionPlanSatisfiability(problem, plan), type)
    }
}
