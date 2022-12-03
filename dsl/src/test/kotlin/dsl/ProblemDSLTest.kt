package dsl

import domain.BlockWorldDomain.Planners
import domain.BlockWorldDomain.Problems
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

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
                    +"arm_empty"()
                    +"clear"("X")
                    +"at"("X", "floor")
                }
                effects {
                    +"at"("X", "arm")
                    -"arm_empty"
                    -"at"("X", "floor")
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
                    +"clear"("X")
                    +"arm_empty"
                    -"at"("X", "arm")
                    -"clear"("Y")
                }
            }

            "unstack" {
                parameters {
                    "X" ofType "blocks"
                    "Y" ofType "locations"
                }
                preconditions {
                    +"on"("X", "Y")
                    +"clear"("X")
                    +"arm_empty"
                }
                effects {
                    -"on"("X", "Y")
                    -"clear"("X")
                    -"arm_empty"
                    +"at"("X", "arm")
                    +"clear"("Y")
                }
            }
            "putdown" {
                parameters {
                    "X" ofType "blocks"
                }
                preconditions {
                    +"at"("X", "arm")
                    +"clear"("Y")
                }
                effects {
                    -"at"("X", "arm")
                    +"clear"("X")
                    +"arm_empty"
                    +"at"("X", "floor")
                }
            }
        }
    }

    private val problemDSL = problem(domainDSL) {
        objects {
            +"blocks"("a", "b", "c", "d")
            +"locations"("floor", "arm")
        }
        initialState {
            +"at"("a", "floor")
            +"at"("b", "floor")
            +"at"("c", "floor")
            +"at"("d", "floor")
            +"arm_empty"
            +"clear"("a")
            +"clear"("b")
            +"clear"("c")
            +"clear"("d")
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
    fun test() {
        Planners.stripsPlanner.plan(Problems.stackAB)
    }

    @Test
    fun testPlanner() {
        Planners.stripsPlanner.plan(problemDSL).toSet().size shouldBe 1
        Planners.stripsPlanner.plan(problemDSL).first().operators.toSet().size shouldBe 2
        Planners.stripsPlanner.plan(problemDSL).first().operators.first().name shouldBe
            Planners.stripsPlanner.plan(Problems.stackAB).first().operators.first().name
        Planners.stripsPlanner.plan(problemDSL).first().operators.last().name shouldBe
            Planners.stripsPlanner.plan(Problems.stackAB).first().operators.last().name
    }
}
