import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.Planners
import resources.TestUtils.Problems

class PlannerTest : AnnotationSpec() {
    @Test
    fun testPlanner() {
        val generatedPlan=Planners.dummyPlanner.plan(Problems.stack).first()
        val plan2check= Plan.of(listOf(TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))))
        generatedPlan === plan2check //Le stupide dataclass controllano il riferimento non la dannata struttura
    }


    @Test
    fun testPlanSequence(){
//        val generatedPlans= Planners.dummyPlanner.plan(Problems.stackAX).map { println(it) }

        for (plan in Planners.dummyPlanner.plan(Problems.stackAX)) {
            println(plan)
        }

//        val pickA= TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.a))
//        var stackAB= TestUtils.Actions.stack.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.a))
//        stackAB = stackAB.apply(VariableAssignment.of(TestUtils.Values.Y, TestUtils.Values.b))
//        val pickC=TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))
//
//        val plan2check= sequenceOf(
//            Plan.of(listOf(pickA,stackAB,pickC)),
//            Plan.of(listOf(pickA, stackAB)))
//        generatedPlans.take(2).toSet() shouldBe plan2check.toSet()
    }
}
