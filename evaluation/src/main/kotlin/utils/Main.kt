package utils

import BaseBenchmark
import core.Planner
import createPlansList
import domain.BlockWorldDomain
import domain.LogisticDomain
import explanation.Explainer
import explanation.impl.QuestionPlanSatisfiability
import java.lang.Exception

/**
 * Utility method to build the benchmarks.
 */
@Suppress("NestedBlockDepth")
fun main(args: Array<String>) {
    val problems = listOf(BlockWorldDomain.Problems.stackAB, LogisticDomain.Problems.robotFromLoc1ToLoc2)
    val maxLength = if (args.isNotEmpty()) args[0].toInt() else 20
    val isWorkflow = args.isNotEmpty()
    val explanationTypes = listOf("C", "")
    fun buildBenchmark() {
        val b = BaseBenchmark()
        for (problem in problems) {
            println("start elab ${problem.domain}")
            val plans = createPlansList(problem, maxLength)
            println("finish elab ${problem.domain}")
            for (j in explanationTypes){
                for (i in 1..5) {
                    try {
                        b.writeBenchmark("", problem, j, i, plans, isWorkflow)
                    } catch (_: Exception) { }
                    println("finish question $i")
                }
                println("X $j question $j")
            }
            println("finish $problem")
        }
    }

    buildBenchmark()
}
