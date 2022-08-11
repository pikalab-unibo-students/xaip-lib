package dsl

import io.kotest.matchers.shouldBe
import resources.TestUtils.domainDSL
import resources.TestUtils.problemDSL

// ktlint-disable filename
/**
 * Test for ProblemDSL cereation.
 */
// TODO Make this an actual test asseting that the result of problem { .. } is an instance of Problem containing all the information provided via DSL
fun main() {
    val p = problem {
        domain = domainDSL
        objects {
            // TODO questo blocco non ha senso, confermo
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
