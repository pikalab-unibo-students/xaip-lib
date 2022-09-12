import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import resources.domain.BlockWorldDomain.Actions
import resources.domain.BlockWorldDomain.Planners
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class StripsPlannerTest : AnnotationSpec() {
    private val pickA = Actions.pick.apply(VariableAssignment.of(Values.X, Values.a))
    private val pickB = Actions.pick.apply(VariableAssignment.of(Values.X, Values.b))
    private val pickC = Actions.pick.apply(VariableAssignment.of(Values.X, Values.c))
    private val pickD = Actions.pick.apply(VariableAssignment.of(Values.X, Values.d))

    private var stackAB = Actions.stack.apply(VariableAssignment.of(Values.X, Values.a))
    private var stackAC = Actions.stack.apply(VariableAssignment.of(Values.X, Values.a))
    private var stackAD = Actions.stack.apply(VariableAssignment.of(Values.X, Values.a))

    private var stackBA = Actions.stack.apply(VariableAssignment.of(Values.X, Values.b))
    private var stackBC = Actions.stack.apply(VariableAssignment.of(Values.X, Values.b))
    private var stackBD = Actions.stack.apply(VariableAssignment.of(Values.X, Values.b))

    private var stackCB = Actions.stack.apply(VariableAssignment.of(Values.X, Values.c))
    private var stackCA = Actions.stack.apply(VariableAssignment.of(Values.X, Values.c))
    private var stackCD = Actions.stack.apply(VariableAssignment.of(Values.X, Values.c))

    private var stackDB = Actions.stack.apply(VariableAssignment.of(Values.X, Values.d))
    private var stackDC = Actions.stack.apply(VariableAssignment.of(Values.X, Values.d))
    private var stackDA = Actions.stack.apply(VariableAssignment.of(Values.X, Values.d))

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
        val plan = Planners.stripsPlanner.plan(Problems.stackCAB).toSet()
        val plan2check = setOf(Plan.of(listOf(pickA, stackAB, pickC, stackCA)))
        plan.size shouldBe 1
        plan shouldBe plan2check
    }
}
