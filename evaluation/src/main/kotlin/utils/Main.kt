package utils

import BaseBenchmark
import core.Planner
import createPlansList
import domain.BlockWorldDomain
import domain.LogisticDomain
import explanation.Explainer
import explanation.impl.QuestionPlanSatisfiability

fun main() {
    val problems = listOf(BlockWorldDomain.Problems.stackAB, LogisticDomain.Problems.robotFromLoc1ToLoc2)
    val maxLength = 10
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
                    b.writeBenchmark("", problem, j, i, plans)
        }
    }

    buildBenchmark()
}