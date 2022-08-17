package dsl // ktlint-disable filename

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Planners
import resources.TestUtils.Problems

/**
 * Test for DomainDSL cereation.
 */
class ProblemDSLTest : AnnotationSpec() {
    private val domainDSL = domain {
        name = "block_world"
        types {
            +"anything"
            +"strings"("anything")
            +"blocks"("strings")
            +"locations"("strings")
        }
        predicates {
            +"at"("blocks", "locations")
            +"on"("blocks", "blocks")
            +"arm_empty"
            +"clear"("blocks")
        }
        actions {
            "pick" {
                parameters {
                    "X" ofType "blocks"
                }
                preconditions {
                    +"at"("X", "floor")
                    +"arm_empty"
                    +"clear"("X")
                }
                effects {
                    +"at"("X", "arm")
                    -"at"("X", "floor")
                    -"arm_empty"
                    -"clear"("X")
                }
            }
            "stack" {
                parameters {
                    "X" ofType "blocks"
                    "Y" ofType "locations"
                }
                preconditions {
                    +"at"("X", "arm")
                    +"clear"("Y")
                }
                effects {
                    +"on"("X", "Y")
                    +"arm_empty"
                    -"on"("X", "arm")
                    -"clear"("Y")
                }
            }
            "unStack" {
                parameters {
                    "X" ofType "blocks"
                    "Y" ofType "locations"
                }
                preconditions {
                    +"on"("X", "Y")
                    +"clear"("X")
                }
                effects {
                    +"at"("X", "floor")
                    +"arm_empty"
                    -"clear"("Y")
                }
            }
        }
    }

    private val problemDSL = problem(domainDSL) {
        objects {
            +"blocks"("a", "b", "c")
            +"locations"("floor", "arm")
        }
        initialState {
            +"at"("a", "floor")
            +"at"("b", "floor")
            +"at"("c", "floor")
            +"arm_empty"
            +"clear"("a")
            +"clear"("b")
            +"clear"("c")
        }
        goals {
            +"at"("b", "floor")
            +"on"("a", "b")
        }
    }

    @Test
    fun testProblem() {
        problemDSL.domain.name shouldBe Problems.stackAB.domain.name
        problemDSL.goal shouldBe Problems.stackAB.goal
        problemDSL.objects shouldBe Problems.stackAB.objects
    }

    @Test
    fun testPlanner() {
        Planners.dummyPlanner.plan(problemDSL).toSet().size shouldBe 1
        Planners.dummyPlanner.plan(problemDSL).first().actions.toSet().size shouldBe 2
        Planners.dummyPlanner.plan(problemDSL).first().actions.first().name shouldBe
            Planners.dummyPlanner.plan(Problems.stackAB).first().actions.first().name
        Planners.dummyPlanner.plan(problemDSL).first().actions.last().name shouldBe
            Planners.dummyPlanner.plan(Problems.stackAB).first().actions.last().name
    }
}
