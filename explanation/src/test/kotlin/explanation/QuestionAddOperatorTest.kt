package explanation

import domain.BlockWorldDomain.Operators
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionAddOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class QuestionAddOperatorTest : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())
    @Test
    fun `Use pickA instead of pick B in armNotEmpty problem`() {
        val q1 = QuestionAddOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(Operators.pickB)),
            Operators.pickA,
            0
        )

        val explanation = Explanation.of(q1, explainer)
        explanation.originalPlan shouldBe q1.plan
        explanation.novelPlan shouldBe Plan.of(listOf(Operators.pickA))
        explanation.addList shouldBe listOf(Operators.pickA)
        explanation.deleteList shouldBe listOf(Operators.pickB)
        explanation.existingList shouldBe emptyList()
        explanation.isPlanValid() shouldBe true
    }
}
