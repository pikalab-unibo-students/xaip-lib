import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import resources.TestUtils
import resources.TestUtils.Planners
import resources.TestUtils.Problems

class StripsPlannerTest : AnnotationSpec() {

    @Test
    fun testPlanner() {
        val generatedPlan = Planners.dummyPlanner.plan(Problems.stack).first()
        val plan2check = Plan.of(listOf(TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))))
        generatedPlan shouldBe plan2check
    }

    @Test
    fun testPlanSequence() {
        val pickA = TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.a))
        val pickB = TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.b))
        val pickC = TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))

        var stackAB = TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.a))
        stackAB = stackAB.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.b))
        var stackAC = TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.a))
        stackAC = stackAC.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.c))

        var stackBA = TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.b))
        stackBA = stackBA.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.a))
        var stackBC = TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.b))
        stackBC = stackBC.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.c))

        var stackCB = TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))
        stackCB = stackCB.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.b))
        var stackCA = TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))
        stackCA = stackCA.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.a))

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

        val exception = shouldThrow<UnsupportedOperationException> {
            plan.toSet().size shouldBe 0
        }
        exception.message shouldStartWith ("Axioms are not yet supported")
    }
}
