package utils

import BaseBenchmark
import core.Planner
import createPlansList
import domain.BlockWorldDomain
import domain.LogisticDomain
import explanation.Explainer
import explanation.impl.QuestionPlanSatisfiability

/**
 * Utility method to build the benchmarks.
 */
private fun main(args: Array<String>) {
    val problems = listOf(BlockWorldDomain.Problems.stackAB, LogisticDomain.Problems.robotFromLoc1ToLoc2)
    val maxLength = if (args.isNotEmpty()) args[0].toInt() else 100
    val isWorkflow = args.isNotEmpty()
    val explanationTypes = listOf("C", "")
    fun buildBenchmark() {
        val b = BaseBenchmark()
        for (problem in problems) {
            val plans = createPlansList(problem, maxLength)
            val notValid = plans.all {
                !Explainer.of(Planner.strips()).explain(QuestionPlanSatisfiability(problem, it)).isPlanValid()
            }
            if (notValid) error("Plan not valid")
            for (j in explanationTypes)
                for (i in 1..5)
                    b.writeBenchmark("", problem, j, i, plans, isWorkflow)
        }
    }

    buildBenchmark()
}
