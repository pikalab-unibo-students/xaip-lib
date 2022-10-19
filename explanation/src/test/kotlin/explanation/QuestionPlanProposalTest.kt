package explanation

import domain.BlockWorldDomain.Operators
import domain.GraphDomain
import explanation.impl.QuestionPlanProposal
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import domain.BlockWorldDomain.Problems as BlockWorldProblem
import domain.GraphDomain.Problems as GraphProblem
// import domain.GraphDomain.Operators.

class QuestionPlanProposalTest : AnnotationSpec() {
    private val blockWorldproblem = BlockWorldProblem.armNotEmpty
    private val planPickBStackBA = Plan.of(listOf(Operators.pickB, Operators.stackBA))
    private val planPickB = Plan.of(listOf(Operators.pickB))
    private val planPickBstackBApickC = Plan.of(listOf(Operators.pickB, Operators.stackBA, Operators.pickC))
    private val explainer = Explainer.of(Planner.strips())

    private val graphProblemRtoX = GraphProblem.rToX
    private val graphProblemRfromLoc1Loc2 = GraphProblem.robotFromLoc1ToLoc2
    private val planRfromL1toL2 = Plan.of(listOf(GraphDomain.Operators.moveRfromL1toL2))
    private val planRfromL1toL5 = Plan.of(listOf(GraphDomain.Operators.moveRfromL1toL5))

    @Test
    fun `Test valid plan`() {
        val q4 = QuestionPlanProposal(blockWorldproblem, planPickBstackBApickC, planPickB, planPickB.operators.first(), 0)
        val explanation = Explanation.of(q4, explainer)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Test incorrect plan`() {
        val q4 = QuestionPlanProposal(blockWorldproblem, planPickBStackBA, planPickB, planPickB.operators.first(), 0)
        val explanation = Explanation.of(q4, explainer)
        explanation.isPlanValid() shouldBe false
    }

    @Test
    fun `Graph domain test valid plan`() {
        val q4 = QuestionPlanProposal(
            graphProblemRtoX, planRfromL1toL2,
            planRfromL1toL5,
            planRfromL1toL2.operators.first(),
            0
        )
        val explanation = Explanation.of(q4, explainer)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Graph domain est incorrect plan`() {
        val q4 = QuestionPlanProposal(
            graphProblemRfromLoc1Loc2,
            planRfromL1toL2,
            planRfromL1toL5,
            planRfromL1toL2.operators.first(),
            0
        )
        val explanation = Explanation.of(q4, explainer)
        explanation.isPlanValid() shouldBe false
    }
}
