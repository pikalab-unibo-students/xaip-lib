import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.putdownA
import resources.domain.BlockWorldDomain.Operators.stackAB
import resources.domain.BlockWorldDomain.Operators.stackAC
import resources.domain.BlockWorldDomain.Operators.stackAD
import resources.domain.BlockWorldDomain.Operators.stackBA
import resources.domain.BlockWorldDomain.Operators.stackBC
import resources.domain.BlockWorldDomain.Operators.stackBD
import resources.domain.BlockWorldDomain.Operators.stackCA
import resources.domain.BlockWorldDomain.Operators.stackCB
import resources.domain.BlockWorldDomain.Operators.stackCD
import resources.domain.BlockWorldDomain.Operators.stackDA
import resources.domain.BlockWorldDomain.Operators.stackDB
import resources.domain.BlockWorldDomain.Operators.stackDC
import resources.domain.BlockWorldDomain.Operators.unstackAB
import resources.domain.BlockWorldDomain.Planners
import resources.domain.BlockWorldDomain.Problems

class StripsPlannerTest : AnnotationSpec() {

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
    fun testPickXFloorY() {
        val plans = Planners.stripsPlanner.plan(Problems.pickXfloorY).toSet()
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
    fun testStackXY() {
        val plans = Planners.stripsPlanner.plan(Problems.stackXY).toSet()
        val plan2check = setOf(
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
        plans.size shouldBe 12
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testStackXYpickW() {
        val plans = Planners.stripsPlanner.plan(Problems.stackXYpickW).toSet() // caso sfigato
        val plan2check = setOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC)),
            Plan.of(listOf(pickA, stackAD)),

            Plan.of(listOf(pickB, stackBA)),
            Plan.of(listOf(pickB, stackBC)),
            Plan.of(listOf(pickB, stackBD)),

            Plan.of(listOf(pickC, stackCA)),
            Plan.of(listOf(pickC, stackCB)),
            Plan.of(listOf(pickC, stackCD)),

            Plan.of(listOf(pickD, stackDA)),
            Plan.of(listOf(pickD, stackDB)),
            Plan.of(listOf(pickD, stackDC))
        )
        plans.size shouldBe 12
        plans shouldBe plan2check
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
}
