import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import resources.domain.BlockWorldDomain.Actions
import resources.domain.BlockWorldDomain.Planners
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class StripsPlannerTest : AnnotationSpec() {
    private val pickA = Operator.of(Actions.pick).apply(VariableAssignment.of(Values.X, Values.a))
    private val pickB = Operator.of(Actions.pick).apply(VariableAssignment.of(Values.X, Values.b))
    private val pickC = Operator.of(Actions.pick).apply(VariableAssignment.of(Values.X, Values.c))
    private val pickD = Operator.of(Actions.pick).apply(VariableAssignment.of(Values.X, Values.d))

    private var stackAB = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.a))
    private var stackAC = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.a))
    private var stackAD = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.a))

    private var stackBA = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.b))
    private var stackBC = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.b))
    private var stackBD = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.b))

    private var stackCB = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.c))
    private var stackCA = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.c))
    private var stackCD = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.c))

    private var stackDB = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.d))
    private var stackDC = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.d))
    private var stackDA = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.d))

    init {
        stackAB = stackAB.apply(VariableAssignment.of(Values.Y, Values.b))
        stackAC = stackAC.apply(VariableAssignment.of(Values.Y, Values.c))
        stackAD = stackAD.apply(VariableAssignment.of(Values.Y, Values.d))

        stackBA = stackBA.apply(VariableAssignment.of(Values.Y, Values.a))
        stackBC = stackBC.apply(VariableAssignment.of(Values.Y, Values.c))
        stackBD = stackBD.apply(VariableAssignment.of(Values.Y, Values.d))

        stackCB = stackCB.apply(VariableAssignment.of(Values.Y, Values.b))
        stackCA = stackCA.apply(VariableAssignment.of(Values.Y, Values.a))
        stackCD = stackCD.apply(VariableAssignment.of(Values.Y, Values.d))

        stackDA = stackDA.apply(VariableAssignment.of(Values.Y, Values.a))
        stackDB = stackDB.apply(VariableAssignment.of(Values.Y, Values.b))
        stackDC = stackDC.apply(VariableAssignment.of(Values.Y, Values.c))
    }

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
}
