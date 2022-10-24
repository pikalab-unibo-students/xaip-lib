package explanation

import Plan
import State
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackAC
import domain.BlockWorldDomain.Problems
import domain.BlockWorldDomain.States
import domain.GraphDomain.Operators.moveRfromL1toL2
import domain.GraphDomain.Operators.moveRfromL1toL5
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import domain.GraphDomain.Problems as GraphProblem

class QuestionReplaceOperatorTest : AnnotationSpec() {
    @Test
    fun `Replace stackAB with stackAC in stackZWpickX problem`() {
        val q3 = QuestionReplaceOperator(
            Problems.stackZWpickX,
            Plan.of(listOf(pickA, stackAB, pickD)),
            stackAC,
            1,
            State.of(
                Fluents.atAArm,
                Fluents.clearA,
                Fluents.atCFloor,
                Fluents.clearC,
                Fluents.atDFloor,
                Fluents.clearD,
                Fluents.clearB,
                Fluents.atBFloor,
                Fluents.armEmpty
            )
        )
        val explanation = Explainer.of(Planner.strips(), q3).explain()

        explanation.originalPlan shouldBe q3.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickA, stackAC, pickD))
        explanation.addList shouldBe listOf(stackAC)
        explanation.deleteList shouldBe listOf(stackAB)
        explanation.existingList shouldBe listOf(pickA, pickD)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Replace pickC with pickD in stackZWpickX problem`() {
        val q3 = QuestionReplaceOperator(
            Problems.stackZWpickX,
            Plan.of(listOf(pickA, stackAB, pickC)),
            pickD,
            2,
            State.of(
                Fluents.onAB,
                Fluents.clearA,
                Fluents.atCFloor,
                Fluents.clearC,
                Fluents.atDFloor,
                Fluents.clearD,
                Fluents.atBFloor,
                Fluents.armEmpty
            )
        )
        val explanation = Explainer.of(Planner.strips(), q3).explain()

        explanation.originalPlan shouldBe q3.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickA, stackAB, pickD))
        explanation.addList shouldBe listOf(pickD)
        explanation.deleteList shouldBe listOf(pickC)
        explanation.existingList shouldBe listOf(pickA, stackAB)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Replace pickA with pickC in stackAB problem`() {
        val q3 = QuestionReplaceOperator(
            Problems.stackAB,
            Plan.of(listOf(pickC, stackAB)),
            pickA,
            0,
            States.initial
        )
        val explanation = Explainer.of(Planner.strips(), q3).explain()

        explanation.originalPlan shouldBe q3.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickA, stackAB))
        explanation.addList shouldBe listOf(pickA)
        explanation.deleteList shouldBe listOf(pickC)
        explanation.existingList shouldBe listOf(stackAB)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Replace moveRfromL1toL2 with moveRfromL1toL5 in the plan`() {
        val planRfromL1toL2 = Plan.of(listOf(moveRfromL1toL2))
        val graphProblemRtoX = GraphProblem.rToX
        val q3 = QuestionReplaceOperator(
            graphProblemRtoX,
            planRfromL1toL2,
            moveRfromL1toL5,
            0
        )
        val explanation = Explainer.of(Planner.strips(), q3).explain()

        explanation.originalPlan shouldBe q3.plan
        explanation.novelPlan shouldBe Plan.of(listOf(moveRfromL1toL5))
        explanation.addList shouldBe listOf(moveRfromL1toL5)
        explanation.deleteList shouldBe listOf(moveRfromL1toL2)
        explanation.existingList shouldBe emptyList()
        explanation.isPlanValid() shouldBe true
    }
}
