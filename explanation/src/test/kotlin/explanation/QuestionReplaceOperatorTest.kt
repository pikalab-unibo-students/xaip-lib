package explanation

import Plan
import State
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class QuestionReplaceOperatorTest : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())
    @Test
    fun `Remove pickA from the plan to solve the armNotEmpty problem`() {
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
}
