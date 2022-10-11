package explanation

import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Problems
import explanation.impl.Question5
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class Question5Test : AnnotationSpec() {

    @Test
    fun `Scrivi il test`() {
        val q5 = Question5(Problems.pickX, Plan.of(listOf(pickA)), pickA, Plan.of(listOf(pickA)), 0)
        val explanation = Explanation.of(q5.plan, q5.alternativePlan, q5)
        explanation.isPlanValid() shouldBe true
    }
}
