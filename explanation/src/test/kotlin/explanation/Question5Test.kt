package explanation

import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionPlanSatisfiability
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class Question5Test : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())
    @Test
    fun `Scrivi il test`() {
        val q5 = QuestionPlanSatisfiability(Problems.pickX, Plan.of(listOf(pickA)), pickA, Plan.of(listOf(pickA)), 0)
        val explanation = Explanation.of(q5, explainer)
        explanation.isPlanValid() shouldBe true
    }
}
