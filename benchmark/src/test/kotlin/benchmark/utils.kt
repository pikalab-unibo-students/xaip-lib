package benchmark // ktlint-disable filename

import core.Planner
import explanation.Explainer
import explanation.Question
import explanation.impl.ContrastiveExplanationPresenter
import java.lang.management.ManagementFactory

fun measureTimeMillis(question: Question, explanationType: String): Long {
    val start = System.currentTimeMillis()
    if (explanationType.startsWith("c") || explanationType.startsWith("C")) {
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()
    } else {
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).present()
    }
    return System.currentTimeMillis() - start
}

fun measureMemory2(question: Question, explanationType: String): Long {
    val mbean = ManagementFactory.getMemoryMXBean()
    val beforeHeapMemoryUsage = mbean.heapMemoryUsage
    if (explanationType.startsWith("c") || explanationType.startsWith("C")) {
        val instance = ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()
    } else {
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).present()
    }
    val afterHeapMemoryUsage = mbean.heapMemoryUsage
    return afterHeapMemoryUsage.used - beforeHeapMemoryUsage.used
}

fun measureMemory(question: Question, explanationType: String): Long {
    val beforeMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()

    if (explanationType.startsWith("c") || explanationType.startsWith("C")) {
        val instance = ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()
    } else {
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).present()
    }
    val afterMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    return afterMemoryUsage - beforeMemoryUsage
}
