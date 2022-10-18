package explanation

import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.stackAB
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
    fun `Use pickC instead of pick B in armNotEmpty problem`() {
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
    fun `Use pickA instead of pickC in pickC problem`() {
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
}
