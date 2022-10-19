package explanation

import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Problems
import domain.GraphDomain
import domain.GraphDomain.Operators.moveRfromL1toL2
import domain.GraphDomain.Operators.moveRfromL2toL1
import domain.GraphDomain.Operators.loadC1fromL2onR
import domain.GraphDomain.Operators.moveRfromL2toL4
import domain.GraphDomain.Operators.unloadC1fromRtoL4
import explanation.impl.QuestionAddOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
class QuestionAddOperatorTest : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())

    @Test
    fun `Execute pickA to reach the goal (pickA replace pickB in the plan to solve armNotEmptyProblem)`() {
        val q1 = QuestionAddOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(pickB)),
            pickA,
            0
        )

        val explanation = Explanation.of(q1, explainer)
        explanation.originalPlan shouldBe q1.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickA))
        explanation.addList shouldBe listOf(pickA)
        explanation.deleteList shouldBe listOf(pickB)
        explanation.existingList shouldBe emptyList()
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Execute pickC to reach the goal (pickC replace pickB in the plan to solve armNotEmptyProblem)`() {
        val q1 = QuestionAddOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(pickB)),
            pickC,
            0
        )

        val explanation = Explanation.of(q1, explainer)
        explanation.originalPlan shouldBe q1.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickC))
        explanation.addList shouldBe listOf(pickC)
        explanation.deleteList shouldBe listOf(pickB)
        explanation.existingList shouldBe emptyList()
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Add useless operator (pickA) to the plan pickC in pickC problem`() {
        val q1 = QuestionAddOperator(
            Problems.pickC,
            Plan.of(listOf(pickC)),
            pickA,
            0
        )

        val explanation = Explanation.of(q1, explainer)
        explanation.originalPlan shouldBe q1.plan

        explanation.novelPlan shouldBe Plan.of(listOf(pickA, stackAB, pickC))
        explanation.addList shouldBe listOf(pickA, stackAB)
        explanation.deleteList shouldBe emptyList()
        explanation.existingList shouldBe listOf(pickC)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Add useless operator (moveRfromL2toL1) to the plan`() {
        val q1 = QuestionAddOperator(
            GraphDomain.Problems.robotFromLoc1ToLoc2ContainerFromLocation2ToLocation4,
            Plan.of(
                listOf(
                    moveRfromL1toL2,
                    loadC1fromL2onR,
                    moveRfromL2toL4,
                    unloadC1fromRtoL4
                )
            ),
            moveRfromL2toL1,
            0
        )

        val explanation = Explanation.of(q1, explainer)
        explanation.isPlanLengthAcceptable() shouldBe true
        explanation.isProblemSolvable() shouldBe true
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Add useless operator (moveRfromL2toL1) to the plan moveRfromL1toL2 in robotFromLoc1ToLoc2 problem`() {
        val q1 = QuestionAddOperator(
            GraphDomain.Problems.robotFromLoc1ToLoc2,
            Plan.of(
                listOf(
                    moveRfromL1toL2
                )
            ),
            moveRfromL2toL1,
            0
        )

        val explanation = Explanation.of(q1, explainer)
        println(explanation.novelPlan.operators)
        explanation.isPlanLengthAcceptable() shouldBe true
        explanation.isProblemSolvable() shouldBe true
        explanation.isPlanValid() shouldBe false
    }
}