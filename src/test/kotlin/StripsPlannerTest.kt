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
    fun testStackABatCarm() {
        val generatedPlan = Planners.stripsPlanner.plan(Problems.stackABatCarm).first()
        val plan2check = Plan.of(listOf(Actions.pick.apply(VariableAssignment.of(Values.X, Values.c))))
        generatedPlan shouldBe plan2check
    }

    @Test
    fun testStackAX() {
        val plansGenerated1 = Planners.stripsPlanner.plan(Problems.stackAX)
        val plan2check1 = listOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC)),
            Plan.of(listOf(pickA, stackAD))
        )
        plansGenerated1.toSet().size shouldBe 3
        plansGenerated1.toSet() shouldBe plan2check1.toSet()
    }

    @Test
    fun testPickX() {
        val plansGenerated2 = Planners.stripsPlanner.plan(Problems.pickX)
        val plan2check2 = listOf(
            Plan.of(listOf(pickA)),
            Plan.of(listOf(pickB)),
            Plan.of(listOf(pickC)),
            Plan.of(listOf(pickD))
        )
        plansGenerated2.toSet().size shouldBe 4
        plansGenerated2.toSet() shouldBe plan2check2.toSet()
    }

    @Test
    fun testPickXFloorY() {
        val plansGenerated3 = Planners.stripsPlanner.plan(Problems.pickXfloorY)
        val plan2check2 = listOf(
            Plan.of(listOf(pickA)),
            Plan.of(listOf(pickB)),
            Plan.of(listOf(pickC)),
            Plan.of(listOf(pickD))
        )
        plansGenerated3.toSet().size shouldBe 4
        plansGenerated3.toSet() shouldBe plan2check2.toSet()
    }

    @Test
    fun testStackXY() {
        val plansGenerated4 = Planners.stripsPlanner.plan(Problems.stackXY)
        val plan2check4 = listOf(
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
        plansGenerated4.toSet().size shouldBe 12
        plansGenerated4.toSet() shouldBe plan2check4.toSet()
    }

    @Test
    fun testStackXYpickW() {
        val plansGenerated5 = Planners.stripsPlanner.plan(Problems.stackXYpickW) // caso sfigato
        val plan2check5 = setOf(
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
        plansGenerated5.toSet().size shouldBe 12
        plansGenerated5.toSet() shouldBe plan2check5
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
        val plan = Planners.stripsPlanner.plan(Problems.stackABC)
        println(plan.toSet())
    }
}
