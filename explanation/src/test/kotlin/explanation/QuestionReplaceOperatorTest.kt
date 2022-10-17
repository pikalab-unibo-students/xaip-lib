package explanation

import Plan
import State
import domain.BlockWorldDomain
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.putdownC
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackAD
import domain.BlockWorldDomain.Operators.unstackAB
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class QuestionReplaceOperatorTest : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())

    @Test
    fun `Replace pickC with pickD in stackZWpickX problem`() {
        val q3 = QuestionReplaceOperator(
            Problems.stackZWpickX,
            Plan.of(listOf(pickA, stackAB, pickC)),
            pickC,
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
            ),
            pickD
        )

        val explanation = Explanation.of(q3, explainer)

        explanation.originalPlan shouldBe q3.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickA, stackAB, pickD))
        explanation.addList shouldBe listOf(pickD)
        explanation.deleteList shouldBe listOf(pickC)
        explanation.existingList shouldBe listOf(pickA, stackAB)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Replace pickC with unstackAB in stackZWpickX problem`() {
        val q3 = QuestionReplaceOperator(
            Problems.stackZWpickX,
            Plan.of(listOf(pickA, stackAB, pickC)),
            pickC,
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
            ),
            unstackAB
        )

        val explanation = Explanation.of(q3, explainer)

        explanation.originalPlan shouldBe q3.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickA, stackAB, unstackAB, stackAD, pickC))
        explanation.addList shouldBe listOf(unstackAB, stackAD)
        explanation.deleteList shouldBe emptyList()
        explanation.existingList shouldBe listOf(pickA, stackAB, pickC)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Replace pickA with pickC in stackAB problem`() {
        val q3 = QuestionReplaceOperator(
            Problems.stackAB,
            Plan.of(listOf(pickA, stackAB)),
            pickA,
            0,
            BlockWorldDomain.States.initial,
            pickC
        )

        val explanation = Explanation.of(q3, explainer)

        explanation.originalPlan shouldBe q3.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickC, putdownC, pickA, stackAB))
        explanation.addList shouldBe listOf(pickC, putdownC)
        explanation.deleteList shouldBe emptyList()
        explanation.existingList shouldBe listOf(pickA, stackAB)
        explanation.isPlanValid() shouldBe true
    }
}
