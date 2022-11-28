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
            println("start elaborating plans ${problems.indexOf(problem)}")
            val plans = createPlansList(problem, maxLength)
            println("end elaboration plans ${problems.indexOf(problem)}")
            for (j in explanationTypes){
                for (i in 1..5) {
                    try {
                        b.writeBenchmark("", problem, j, i, plans, isWorkflow)
                    } catch (_: Exception) {
                        println("handle exception")
                    }
                    println("passing to question $i")
                }
                println("end explanation $j")

            }
            println("end explanations for ${problems.indexOf(problem)}")
            if(problems.indexOf(problem) == 1) break
        }
        println("out")
    }

    buildBenchmark()
}
