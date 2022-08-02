import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import resources.TestUtils
import resources.TestUtils.Actions
import resources.TestUtils.Planners
import resources.TestUtils.Problems
import resources.TestUtils.Values

class StripsPlannerTest : AnnotationSpec() {

    @Test
    fun testPlanner() {
        val generatedPlan = Planners.dummyPlanner.plan(Problems.stack).first()
        val plan2check = Plan.of(listOf(Actions.pick.apply(VariableAssignment.of(Values.X, TestUtils.Values.c))))
        generatedPlan shouldBe plan2check
    }

    @Test
    fun testStackAX() {
        val pickA = Actions.pick.apply(VariableAssignment.of(Values.X, Values.a))

        var stackAB = Actions.stack.apply(VariableAssignment.of(Values.X, Values.a))
        stackAB = stackAB.apply(VariableAssignment.of(Values.Y, Values.b))
        var stackAC = Actions.stack.apply(VariableAssignment.of(Values.X, Values.a))
        stackAC = stackAC.apply(VariableAssignment.of(Values.Y, Values.c))

        val plansGenerated1 = Planners.dummyPlanner.plan(Problems.stackAX)
        val plan2check1 = listOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC))
        )

        plansGenerated1.toSet().size shouldBe 2

        plansGenerated1.toSet() shouldBe plan2check1.toSet()
    }

    @Test
    fun testPlanSequence() {
        val pickA = Actions.pick.apply(VariableAssignment.of(Values.X, Values.a))
        val pickB = Actions.pick.apply(VariableAssignment.of(Values.X, Values.b))
        val pickC = Actions.pick.apply(VariableAssignment.of(Values.X, Values.c))

        var stackAB = Actions.stack.apply(VariableAssignment.of(Values.X, Values.a))
        stackAB = stackAB.apply(VariableAssignment.of(Values.Y, Values.b))
        var stackAC = Actions.stack.apply(VariableAssignment.of(Values.X, Values.a))
        stackAC = stackAC.apply(VariableAssignment.of(Values.Y, Values.c))

        var stackBA = Actions.stack.apply(VariableAssignment.of(Values.X, Values.b))
        stackBA = stackBA.apply(VariableAssignment.of(Values.Y, Values.a))
        var stackBC = Actions.stack.apply(VariableAssignment.of(Values.X, Values.b))
        stackBC = stackBC.apply(VariableAssignment.of(Values.Y, Values.c))

        var stackCB = Actions.stack.apply(VariableAssignment.of(Values.X, Values.c))
        stackCB = stackCB.apply(VariableAssignment.of(Values.Y, Values.b))
        var stackCA = Actions.stack.apply(VariableAssignment.of(Values.X, Values.c))
        stackCA = stackCA.apply(VariableAssignment.of(Values.Y, Values.a))

        val plansGenerated1 = Planners.dummyPlanner.plan(Problems.stackAX)
        val plansGenerated2 = Planners.dummyPlanner.plan(Problems.pickX)
        val plansGenerated3 = Planners.dummyPlanner.plan(Problems.pickXfloorY)
        val plansGenerated4 = Planners.dummyPlanner.plan(Problems.stackXY)
        val plansGenerated5 = Planners.dummyPlanner.plan(Problems.stackXYpickW) // caso sfigato

        val plan2check1 = listOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC))
        )

        val plan2check2 = listOf(
            Plan.of(listOf(pickA)),
            Plan.of(listOf(pickB)),
            Plan.of(listOf(pickC))
        )

        val plan2check4 = listOf(
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA, stackAC)),
            Plan.of(listOf(pickB, stackBA)),
            Plan.of(listOf(pickB, stackBC)),
            Plan.of(listOf(pickC, stackCB)),
            Plan.of(listOf(pickC, stackCA))
        )

        val plan2check5 = listOf(
            Plan.of(listOf(pickA, stackAB, pickC)),
            Plan.of(listOf(pickA, stackAC, pickB)),
            Plan.of(listOf(pickB, stackBA, pickC)),
            Plan.of(listOf(pickB, stackBC, pickA)),
            Plan.of(listOf(pickC, stackCA, pickB)),
            Plan.of(listOf(pickC, stackCB, pickA))
        )

        plansGenerated1.toSet().size shouldBe 2
        plansGenerated2.toSet().size shouldBe 3
        plansGenerated3.toSet().size shouldBe 3
        plansGenerated4.toSet().size shouldBe 6
        plansGenerated5.toSet().size shouldBe 6

        plansGenerated1.toSet() shouldBe plan2check1.toSet()
        plansGenerated2.toSet() shouldBe plan2check2.toSet()
        plansGenerated3.toSet() shouldBe plan2check2.toSet()
        plansGenerated4.toSet() shouldBe plan2check4.toSet()
        plansGenerated5.toSet() shouldBe plan2check5.toSet()
    }

    @Test
    fun testAxiomException() {
        val plan = Planners.dummyPlanner.plan(Problems.axiomException)

        val exception = shouldThrow<IllegalStateException> {
            plan.toSet().size shouldBe 0
        }
        exception.message shouldStartWith ("Axioms are not yet supported")
    }
}
