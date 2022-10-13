import domain.BlockWorldDomain.Operators.pickB
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.unstackAB
import domain.BlockWorldDomain.Problems
import explanation.impl.ExplanationExtended
import explanation.impl.QuestionAddOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class ExplanationExtendedTest : AnnotationSpec() {
    @Test
    fun `Test`() {
        val q1 = QuestionAddOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(pickB)),
            pickA,
            0
        )

        val explanationExtended = ExplanationExtended(q1)
        explanationExtended.isPlanLengthAcceptable() shouldBe true
        explanationExtended.isProblemSolvable() shouldBe true
        explanationExtended.isIdempotentActions(stackAB, unstackAB) shouldBe true
    }
}