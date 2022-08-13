package dsl // ktlint-disable filename

import io.kotest.matchers.shouldBe
import resources.TestUtils.problemDSL

/**
 * Test for ProblemDSL cereation.
 */
fun main() {
    val d = domain {}
    val p = problem(d) {
        objects {
            +"blocks"("a", "b")
        }
        initialState {
            +"at"("a", "floor")
            +"at"("b", "floor")
            +"at"("c", "floor")
            +"clear"("a")
            +"clear"("b")
            +"clear"("c")
        }
        goals {
            +"at"("a", "floor")
            +"at"("b", "floor")
        }
    }

    p shouldBe problemDSL
}
