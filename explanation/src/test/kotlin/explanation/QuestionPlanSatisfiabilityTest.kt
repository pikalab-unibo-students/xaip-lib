package explanation

import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.putdownA
import domain.GraphDomain.Operators.moveRfromL1toL2
import domain.GraphDomain.Operators.moveRfromL1toL5
import domain.BlockWorldDomain.Problems
import domain.GraphDomain
import explanation.impl.QuestionPlanSatisfiability
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class QuestionPlanSatisfiabilityTest : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())
    private val graphProblemRfromLoc1Loc2 = GraphDomain.Problems.robotFromLoc1ToLoc2
    private val planRfromL1toL2 = Plan.of(listOf(moveRfromL1toL2))
    private val planRfromL1toL5 = Plan.of(listOf(moveRfromL1toL5))

    @Test
    fun `BlockWorld plan valid`() {
        val q5 = QuestionPlanSatisfiability(Problems.pickX, Plan.of(listOf(pickA)))
        val explanation = Explanation.of(q5, explainer)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `BlockWorld plan not valid`() {
        val q5 = QuestionPlanSatisfiability(
            Problems.pickX,
            Plan.of(listOf(pickA, putdownA))
        )
        val explanation = Explanation.of(q5, explainer)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe false
    }

    @Test
    fun `GraphDomain plan valid`() {
        val q5 = QuestionPlanSatisfiability(
            graphProblemRfromLoc1Loc2, planRfromL1toL2
        )
        val explanation = Explanation.of(q5, explainer)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `GraphDomain plan not valid`() {
        val q5 = QuestionPlanSatisfiability(
            graphProblemRfromLoc1Loc2, planRfromL1toL5
        )
        val explanation = Explanation.of(q5, explainer)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe false
    }
}
