package explanation

import domain.BlockWorldDomain.Operators
import domain.GraphDomain.Operators.moveRfromL1toL2
import domain.GraphDomain.Operators.moveRfromL1toL5
import explanation.impl.ContrastiveExplanationPresenter
import explanation.impl.QuestionPlanProposal
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import domain.BlockWorldDomain.Problems as BlockWorldProblem
import domain.GraphDomain.Problems as GraphProblem

class QuestionPlanProposalTest : AnnotationSpec() {
    private val blockWorldproblem = BlockWorldProblem.armNotEmpty
    private val planPickBStackBA = Plan.of(listOf(Operators.pickB, Operators.stackBA))
    private val planPickB = Plan.of(listOf(Operators.pickB))
    private val planPickBstackBApickC = Plan.of(listOf(Operators.pickB, Operators.stackBA, Operators.pickC))

    private val graphProblemRtoX = GraphProblem.rToX
    private val graphProblemRfromLoc1Loc2 = GraphProblem.robotFromLoc1ToLoc2
    private val planRfromL1toL2 = Plan.of(listOf(moveRfromL1toL2))
    private val planRfromL1toL5 = Plan.of(listOf(moveRfromL1toL5))

    @Test
    fun `BlockWorld domain test valid plan`() {
        val q4 = QuestionPlanProposal(
            blockWorldproblem,
            planPickBstackBApickC,
            planPickB,
            planPickB.operators.first(),
            0
        )
        val explanation = Explainer.of(Planner.strips(), q4).explain()
        explanation.isPlanValid() shouldBe true
        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())

    }

    @Test
    fun `BlockWorld domain test incorrect plan`() {
        val q4 = QuestionPlanProposal(
            blockWorldproblem,
            planPickBStackBA,
            planPickB,
            planPickB.operators.first(),
            0
        )
        val explanation = Explainer.of(Planner.strips(), q4).explain()
        explanation.isPlanValid() shouldBe false
        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())
    }

    @Test
    fun `Graph domain test valid plan`() {
        val q4 = QuestionPlanProposal(
            graphProblemRtoX,
            planRfromL1toL2,
            planRfromL1toL5,
            planRfromL1toL2.operators.first(),
            0
        )
        val explanation = Explainer.of(Planner.strips(), q4).explain()
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Graph domain test incorrect plan`() {
        val q4 = QuestionPlanProposal(
            graphProblemRfromLoc1Loc2,
            planRfromL1toL5,
            planRfromL1toL2,
            planRfromL1toL2.operators.first(),
            0
        )
        val explanation = Explainer.of(Planner.strips(), q4).explain()
        println(explanation.novelPlan)
        explanation.isPlanValid() shouldBe false
    }
}
