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
    private var stackAB = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.a))
    private var stackAC = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.a))
    private var stackBA = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.b))
    private var stackBC = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.b))
    private var stackCB = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.c))
    private var stackCA = Operator.of(Actions.stack).apply(VariableAssignment.of(Values.X, Values.c))

    init {
        stackAB = stackAB.apply(VariableAssignment.of(Values.Y, Values.b))
        stackAC = stackAC.apply(VariableAssignment.of(Values.Y, Values.c))
        stackBA = stackBA.apply(VariableAssignment.of(Values.Y, Values.a))
        stackBC = stackBC.apply(VariableAssignment.of(Values.Y, Values.c))
        stackCB = stackCB.apply(VariableAssignment.of(Values.Y, Values.b))
        stackCA = stackCA.apply(VariableAssignment.of(Values.Y, Values.a))
    }

    @Test
    fun testStackABpickC() {
        val plans = Planners.dummyPlanner.plan(Problems.stackABpickC).toSet()
        val plan2check = setOf(Plan.of(listOf(pickA, stackAB, pickC)))
        plans.size shouldBe 3
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testStackAX() {
        val plans = Planners.dummyPlanner.plan(Problems.stackAX).toSet()
        val plan2check = setOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC))
        )
        plans.size shouldBe 2
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testPickX() {
        val plans = Planners.dummyPlanner.plan(Problems.pickX).toSet()
        val plan2check = setOf(
            Plan.of(listOf(pickA)),
            Plan.of(listOf(pickB)),
            Plan.of(listOf(pickC))
        )
        plans.size shouldBe 3
        plans shouldBe plan2check
    }

    @Test
    fun testPickXFloorY() {
        val plansGenerated3 = Planners.dummyPlanner.plan(Problems.pickXfloorY)
        val plan2check2 = listOf(
            Plan.of(listOf(pickA)),
            Plan.of(listOf(pickB)),
            Plan.of(listOf(pickC))
        )
        plansGenerated3.toSet().size shouldBe 3
        plansGenerated3.toSet() shouldBe plan2check2.toSet()
    }

    @Test
    fun testStackXY() {
        val plans = Planners.dummyPlanner.plan(Problems.stackXY).toSet()
        val plan2check = setOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC)),
            Plan.of(listOf(pickB, stackBA)),
            Plan.of(listOf(pickB, stackBC)),
            Plan.of(listOf(pickC, stackCB)),
            Plan.of(listOf(pickC, stackCA))
        )
        plans.size shouldBe 6
        plans shouldBe plan2check
    }

    @Test
    fun testStackXYpickW() {
        val plans = Planners.dummyPlanner.plan(Problems.stackXYpickW).toSet() // caso sfigato
        val plan2check = setOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC)),

            Plan.of(listOf(pickB, stackBA)),
            Plan.of(listOf(pickB, stackBC)),

            Plan.of(listOf(pickC, stackCA)),
            Plan.of(listOf(pickC, stackCB))
        )
        plans.size shouldBe 6
        plans shouldBe plan2check
        println(plans)
    }

    @Test
    fun testAxiomException() {
        val plan = Planners.dummyPlanner.plan(Problems.axiomException)
        val exception = shouldThrow<IllegalStateException> {
            plan.toSet().size shouldBe 0
        }
        exception.message shouldStartWith ("Axioms are not yet supported")
    }

    @Test
    fun testStackABC() {
        val plans = Planners.dummyPlanner.plan(Problems.stackABC).toSet()
        val plan2check = setOf(
            Plan.of(listOf(pickA, stackAB, pickC, stackCA))
        )
        plans.size shouldBe 1
        plans shouldBe plan2check
    }
}
