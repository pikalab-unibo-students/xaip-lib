package core

import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.putdownA
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackAC
import domain.BlockWorldDomain.Operators.stackAD
import domain.BlockWorldDomain.Operators.stackBA
import domain.BlockWorldDomain.Operators.stackBC
import domain.BlockWorldDomain.Operators.stackBD
import domain.BlockWorldDomain.Operators.stackCA
import domain.BlockWorldDomain.Operators.stackCB
import domain.BlockWorldDomain.Operators.stackCD
import domain.BlockWorldDomain.Operators.stackDA
import domain.BlockWorldDomain.Operators.stackDB
import domain.BlockWorldDomain.Operators.stackDC
import domain.BlockWorldDomain.Operators.unstackAB
import domain.BlockWorldDomain.Operators.unstackCD
import domain.BlockWorldDomain.Planners
import domain.BlockWorldDomain.Problems
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith

class StripsPlannerTest : AnnotationSpec() {
    val planPickAStackAB = Plan.of(listOf(pickA, stackAB))
    val setPickX = setOf(
        Plan.of(listOf(pickA)),
        Plan.of(listOf(pickB)),
        Plan.of(listOf(pickC)),
        Plan.of(listOf(pickD))
    )
    val setPickXStackXY = setOf(
        Plan.of(listOf(pickA, stackAB)),
        Plan.of(listOf(pickA, stackAC)),
        Plan.of(listOf(pickA, stackAD)),

        Plan.of(listOf(pickB, stackBA)),
        Plan.of(listOf(pickB, stackBC)),
        Plan.of(listOf(pickB, stackBD)),

        Plan.of(listOf(pickC, stackCB)),
        Plan.of(listOf(pickC, stackCD)),
        Plan.of(listOf(pickC, stackCA)),

        Plan.of(listOf(pickD, stackDA)),
        Plan.of(listOf(pickD, stackDC)),
        Plan.of(listOf(pickD, stackDB))
    )

    @Test
    fun testPickC() {
        val plans = Planners.stripsPlanner.plan(Problems.pickC).toSet()
        val plan2check = setOf(Plan.of(listOf(pickC)))
        plans.toSet().size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testStackAX() {
        val plans = Planners.stripsPlanner.plan(Problems.stackAX).toSet()
        val plan2check = setOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC)),
            Plan.of(listOf(pickA, stackAD))
        )
        plans.size shouldBe 3
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testPickX() {
        val plans = Planners.stripsPlanner.plan(Problems.pickX).toSet()
        val plan2check = setPickX
        plans.size shouldBe 4
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testPickXPickY() {
        val plans = Planners.stripsPlanner.plan(Problems.pickXpickY).toSet()
        val plan2check = setPickX
        plans.size shouldBe 4
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testPickXFloorY() {
        val plans = Planners.stripsPlanner.plan(Problems.pickXfloorY).toSet()
        val plan2check = setPickX
        plans.size shouldBe 4
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testStackXY() {
        val plans = Planners.stripsPlanner.plan(Problems.stackXY).toSet()
        val plan2check = setPickXStackXY
        plans.size shouldBe 12
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testStackZWpickX() {
        val plans = Planners.stripsPlanner.plan(Problems.stackZWpickX).toSet()
        plans.size shouldBe 24
        println(plans)
    }

    @Test
    fun testStackXYpickW() {
        val plans = Planners.stripsPlanner.plan(Problems.stackXYpickW).toSet() // caso sfigato
        plans.size shouldBe 24
        println(plans)
    }

    @Test
    fun testAxiomException() {
        val plan = Planners.stripsPlanner.plan(Problems.axiomException)
        val exception = shouldThrow<IllegalStateException> {
            plan.toSet().size shouldBe 0
        }
        exception.message shouldStartWith ("Axioms are not yet supported")
    }

    @Test
    fun testStackABC() {
        val plans = Planners.stripsPlanner.plan(Problems.stackCAB).toSet()
        val plan2check = setOf(Plan.of(listOf(pickA, stackAB, pickC, stackCA)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testUnstackAB() {
        val plans = Planners.stripsPlanner.plan(Problems.unstackAB).toSet()
        val plan2check = setOf(Plan.of(listOf(unstackAB, putdownA)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testArmNotEmpty() {
        val plans = Planners.stripsPlanner.plan(Problems.armNotEmpty).toSet()
        val plan2check = setOf(
            Plan.of(listOf(pickA)),
            Plan.of(listOf(pickB)),
            Plan.of(listOf(pickC)),
            Plan.of(listOf(pickD))
        )
        plans.size shouldBe 4
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testUnstackABstackBC() {
        val plans = Planners.stripsPlanner.plan(Problems.unstackABstackBC).toSet()
        val plan2check = setOf(Plan.of(listOf(unstackAB, putdownA, pickB, stackBC)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testUnstackABstackBCstackAD() {
        val plans = Planners.stripsPlanner.plan(Problems.unstackABstackBCstackAD).toSet()
        val plan2check = setOf(Plan.of(listOf(unstackAB, putdownA, pickB, stackBC)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testUnstackABstackDCB() {
        val plans = Planners.stripsPlanner.plan(Problems.unstackABstackDCB).toSet()
        val plan2check = setOf(Plan.of(listOf(unstackAB, putdownA, pickC, stackCB, pickD, stackDC)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testUstackABunstackCDstackDCA() {
        val plans = Planners.stripsPlanner.plan(Problems.unstackABunstackCDstackDCA).toSet()
        val plan2check = setOf(Plan.of(listOf(unstackAB, putdownA, unstackCD, stackCA, pickD, stackDC)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testUstackABpickB() {
        val plans = Planners.stripsPlanner.plan(Problems.unstackABpickB).toSet()
        val plan2check = setOf(Plan.of(listOf(unstackAB, putdownA, pickB)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testUnstackABStackDCApickB() {
        val plans = Planners.stripsPlanner.plan(Problems.unstackABunstackCDstackDCApickB).toSet()
        val plan2check = setOf(Plan.of(listOf(unstackAB, putdownA, unstackCD, stackCA, pickD, stackDC, pickB)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testUnstackABStackBDCA() {
        val plans = Planners.stripsPlanner.plan(Problems.unstackABunstackCDstackBDCA).toSet()
        val plan2check = setOf(Plan.of(listOf(unstackAB, putdownA, unstackCD, stackCA, pickD, stackDC, pickB, stackBD)))
        plans.size shouldBe 1
        plans shouldBe plan2check
        println(plans)
    }
}
