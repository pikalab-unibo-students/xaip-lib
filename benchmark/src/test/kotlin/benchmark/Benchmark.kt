package benchmark

import core.*
import domain.BlockWorldDomain
import explanation.Explainer
import explanation.Question
import explanation.impl.ContrastiveExplanationPresenter
import explanation.impl.QuestionAddOperator
import io.kotest.core.spec.style.AnnotationSpec
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.management.ManagementFactory

interface Benchmark {
    fun mesureMemory() = Long
    fun mesureTime() = Long
}

class AbstractBenchmark(
    private val problem: Problem,
    private val length: Int,
    private val flag: Int
) : Benchmark {
    var plan: MutableList<Operator> = MutableList(length) { Operator.of(problem.domain.actions.first()) }
    private var q1ResultTime = mutableListOf<Long>()
    private var q1ResultMemory = mutableListOf<Long>()

    init {
        prova()
    }

    private fun measureTimeMillis(question: Question): Long {
        val start = System.currentTimeMillis()
        ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()
        return System.currentTimeMillis() - start
    }

    private fun measureMemory(question: Question): Long {
        val mbean = ManagementFactory.getMemoryMXBean()
        val beforeHeapMemoryUsage = mbean.heapMemoryUsage

        val instance = ContrastiveExplanationPresenter(
            Explainer.of(Planner.strips()).explain(question)
        ).presentContrastiveExplanation()

        val afterHeapMemoryUsage = mbean.heapMemoryUsage
        return afterHeapMemoryUsage.used - beforeHeapMemoryUsage.used
    }

    fun prova() {
        if (flag == 2) {
            var i: Int = 0
            while (i < length) {
                q1ResultTime.add(
                    measureTimeMillis(
                        QuestionAddOperator(
                            problem,
                            Plan.of(plan),
                            Operator.of(problem.domain.actions.last()),
                            i
                        )
                    )
                )
                q1ResultMemory.add(
                    measureMemory(
                        QuestionAddOperator(
                            problem,
                            Plan.of(plan),
                            Operator.of(problem.domain.actions.last()),
                            i
                        )
                    )
                )
                i++
            }
        }
    }

    fun write() {
        fun OutputStream.writeCsv() {
            val writer = bufferedWriter()
            writer.write(""""Domain", "Plan length", "QuestionAddOperator", "position", "Time", "Memory""")
            writer.newLine()
            var i = 1
            q1ResultTime.forEach {
                writer.write(
                    "${problem.domain.name}, ${plan.size}, \"QuestionAddOperator\", " +
                        "$i, $it, ${q1ResultMemory[q1ResultTime.indexOf(it)]}"
                )
                writer.newLine()
                i++
            }
            writer.flush()
        }
        FileOutputStream("filename.csv").apply { writeCsv() }
    }
}

class Test : AnnotationSpec() {
    @Test
    fun prova() {
        val b = AbstractBenchmark(BlockWorldDomain.Problems.pickC, 100, 2).write()
        println(b)
    }
}
