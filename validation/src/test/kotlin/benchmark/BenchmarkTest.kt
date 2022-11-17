package benchmark

import BaseBenchmark
import domain.BlockWorldDomain.Problems.pickC
import domain.LogisticDomain.Problems.robotFromLoc1ToLoc2
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import java.io.File

class BenchmarkTest : AnnotationSpec() {
    private val problems = listOf(pickC, robotFromLoc1ToLoc2)

    @Test
    fun buildBenchmark() {
        for (problem in problems) {
            for (i in 1..5)
                for (j in 50..200 step 50)
                    BaseBenchmark(problem, j, i, "c").write("")
            (File("res/benchmark/${problem.domain.name}").length() > 0) shouldBe true
        }
    }
}
