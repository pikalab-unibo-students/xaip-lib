import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.Planners
import resources.TestUtils.Problems

class StripsPlannerTest : AnnotationSpec() {
    @Ignore
    @Test
    fun testPlanner() {
        val generatedPlan=Planners.dummyPlanner.plan(Problems.stack).first()
        val plan2check= Plan.of(listOf(TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))))
        generatedPlan === plan2check
    }

    //@Ignore
    @Test
    fun testPlanSequence(){
        val pickA= TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.a))
        val pickB =TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.b))
        val pickC=TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))

        var stackAB= TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.a))
        stackAB = stackAB.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.b))
        var stackAC = TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.a))
        stackAC = stackAC.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.c))

        //val plansGenerated1= Planners.dummyPlanner.plan(Problems.stackAX)
        val plansGenerated2= Planners.dummyPlanner.plan(Problems.pickX)
        //val plansGenerated3 = Planners.dummyPlanner.plan(Problems.pickXfloorY).toList()
        //val plansGenerated4 = Planners.dummyPlanner.plan(Problems.stackXY)
        //val plansGenerated5 = Planners.dummyPlanner.plan(Problems.stackXYpickW)

        val plan2check1= listOf(
            Plan.of(listOf(pickA,stackAB)),
            Plan.of(listOf(pickA, stackAC)))

        //plansGenerated1.toSet().size shouldBe 2
        plansGenerated2.toSet().take(2).size shouldBe 3
        //plansGenerated3.toSet().size shouldBe 3
        //plansGenerated4.toSet().size shouldBe 6
        //plansGenerated5.toSet().size shouldBe 6

        //plansGenerated1.toSet() shouldBe plan2check1.toSet()
    }
}
