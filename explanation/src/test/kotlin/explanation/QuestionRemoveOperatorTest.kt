package explanation

import core.Plan
import core.Planner
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.stackBA
import domain.BlockWorldDomain.Operators.stackCA
import domain.BlockWorldDomain.Operators.stackDB
import domain.BlockWorldDomain.Operators.stackDC
import domain.BlockWorldDomain.Problems
import domain.GraphDomain.Operators.moveRfromL1toL2
import explanation.impl.QuestionRemoveOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import domain.GraphDomain.Problems as GraphProblem

class QuestionRemoveOperatorTest : AnnotationSpec() {

    @Test
    fun `Remove pickA from the plan to solve the armNotEmpty problem`() {
        val q2 = QuestionRemoveOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(pickA)),
            pickA
        )

        val explanation = Explainer.of(Planner.strips()).explain(q2)
        explanation.originalPlan shouldBe q2.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickB))
        explanation.addList shouldBe listOf(pickB)
        explanation.deleteList shouldBe listOf(pickA)
        explanation.existingList shouldBe emptyList()
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Remove pickB from the plan to solve the armNotEmpty problem`() {
        val q2 = QuestionRemoveOperator(
            Problems.stackDXA,
            Plan.of(listOf(pickB, stackBA, pickD, stackDB)),
            pickB
        )

        val explanation = Explainer.of(Planner.strips()).explain(q2)
        explanation.originalPlan shouldBe q2.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickC, stackCA, pickD, stackDC))
        explanation.addList shouldBe listOf(pickC, stackCA, stackDC)
        explanation.deleteList shouldBe listOf(pickB, stackBA, stackDB)
        explanation.existingList shouldBe listOf(pickD)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Remove moveRfromL1toL2 from the plan to solve the problem RtoX`() {
        val planRfromL1toL2 = Plan.of(listOf(moveRfromL1toL2))
        val graphProblemRtoX = GraphProblem.rToX
        val q2 = QuestionRemoveOperator(
            graphProblemRtoX,
            planRfromL1toL2,
            moveRfromL1toL2
        )
        val explanation = Explainer.of(Planner.strips()).explain(q2)
        explanation.originalPlan shouldBe q2.plan
        explanation.novelPlan shouldBe Plan.of(emptyList())
        explanation.addList shouldBe emptyList()
        explanation.deleteList shouldBe listOf(moveRfromL1toL2)
        explanation.existingList shouldBe emptyList()
        explanation.isPlanValid() shouldBe true
    }
}
