package explanation

import Plan
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionRemoveOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class QuestionRemoveOperatorTest : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())

    @Test
    fun `Remove pickA from the plan to solve the armNotEmpty problem`() {
        val q2 = QuestionRemoveOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(pickA)),
            pickA,
            0
        )

        val explanation = Explanation.of(q2, explainer)
        explanation.originalPlan shouldBe q2.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickB))
        explanation.addList shouldBe listOf(pickB)
        explanation.deleteList shouldBe listOf(pickA)
        explanation.existingList shouldBe emptyList()
        explanation.isPlanValid() shouldBe true
    }
}
