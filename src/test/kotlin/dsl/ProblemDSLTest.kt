package dsl // ktlint-disable filename

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Planners
import resources.TestUtils.Problems

/**
 * Test for DomainDSL cereation.
 */
class ProblemDSLTest : AnnotationSpec() {
    // private val d = TestUtils.Domains.blockWorld

    private val d = domain {
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
            +"arm_empty"()
            +"clear"("blocks")
        }
        actions {
            "pick" {
                parameters {
                    "X" ofType "block"
                }
                preconditions {
                    +"at"("X", "floor")
                    +"arm_empty"()
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
                    "X" ofType "block"
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
                    "X" ofType "block"
                    "Y" ofType "locations"
                }
                preconditions {
                    +"on"("X", "Y")
                    +"clear"("X")
                }
                effects {
                    +"at"("X", "floor")
                    -"arm_empty"
                    -"clear"("Y")
                }
            }
        }
    }
    /*private val p = Problem.of(
        domain = d,
        objects = TestUtils.ObjectSets.objects,
        initialState = TestUtils.States.initial,
        goal = TestUtils.Goals.onAatBandBonFloor
    )

     */

    // DomainDSLs.blockWorldXDomainDSL

    private val p = problem(d) {
        objects {
            +"blocks"("a", "b", "c")
            +"locations"("floor", "arm")
        }
        initialState {
            +"at"("a", "floor")
            +"at"("b", "floor")
            +"at"("c", "floor")
            +"arm_empty"()
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
    fun testDomain() {
        /*
        TestUtils.domainDSL.types.size shouldBe 4
        TestUtils.Types.blocks shouldBeIn TestUtils.domainDSL.types
        TestUtils.Types.locations shouldBeIn TestUtils.domainDSL.types

        d.actions.size shouldBe 1
        d.actions.first().name shouldBe TestUtils.Actions.stack.name
        d.actions.first().parameters.size shouldBe 2
        d.actions.first().preconditions.size shouldBe 2
        d.actions.first().effects.size shouldBe 4

         */
    }

    @Test
    fun testProblem() {
        p.domain.name shouldBe Problems.stackAB.domain.name
        p.goal shouldBe Problems.stackAB.goal
        p.objects shouldBe Problems.stackAB.objects
    }

    @Test
    fun testPlanner() {
        Planners.dummyPlanner.plan(p).toSet() shouldBe
            Planners.dummyPlanner.plan(Problems.stackAB).toSet()
    }

    @Test
    fun testPlannerDSL() {
        Planners.dummyPlanner.plan(p).toSet() shouldBe
            Planners.dummyPlanner.plan(Problems.stackAB).toSet()
    }
}
