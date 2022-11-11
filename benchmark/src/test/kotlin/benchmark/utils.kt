package benchmark // ktlint-disable filename

import core.Planner
import explanation.Explainer
import explanation.Question
import explanation.impl.ContrastiveExplanationPresenter
import java.lang.management.ManagementFactory

fun measureTimeMillis(question: Question): Long {
    val start = System.currentTimeMillis()
    ContrastiveExplanationPresenter(
        Explainer.of(Planner.strips()).explain(question)
    ).presentContrastiveExplanation()
    return System.currentTimeMillis() - start
}

fun measureMemory2(question: Question): Long {
    val mbean = ManagementFactory.getMemoryMXBean()
    val beforeHeapMemoryUsage = mbean.heapMemoryUsage

    val instance = ContrastiveExplanationPresenter(
        Explainer.of(Planner.strips()).explain(question)
    ).presentContrastiveExplanation()

    val afterHeapMemoryUsage = mbean.heapMemoryUsage
    return afterHeapMemoryUsage.used - beforeHeapMemoryUsage.used
}

fun measureMemory(question: Question): Long {
    val beforeMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()

    val instance = ContrastiveExplanationPresenter(
        Explainer.of(Planner.strips()).explain(question)
    ).presentContrastiveExplanation()

    val afterMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    return afterMemoryUsage - beforeMemoryUsage
}
