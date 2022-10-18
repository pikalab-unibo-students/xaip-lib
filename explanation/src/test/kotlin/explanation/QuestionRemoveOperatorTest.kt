package explanation

import Plan
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.stackBA
import domain.BlockWorldDomain.Operators.stackCA
import domain.BlockWorldDomain.Operators.stackDB
import domain.BlockWorldDomain.Operators.stackDC
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

    @Test
    fun `Remove pick from the plan to solve the armNotEmpty problem`() {
        val q2 = QuestionRemoveOperator(
            Problems.stackDXA,
            Plan.of(listOf(pickB, stackBA, pickD, stackDB)),
            pickB,
            0
        )

        val explanation = Explanation.of(q2, explainer)
        explanation.originalPlan shouldBe q2.plan
        explanation.novelPlan shouldBe Plan.of(listOf(pickC, stackCA, pickD, stackDC))
        explanation.addList shouldBe listOf(pickC, stackCA, stackDC)
        explanation.deleteList shouldBe listOf(pickB, stackBA, stackDB)
        explanation.existingList shouldBe listOf(pickD)
        explanation.isPlanValid() shouldBe true
    }
}
