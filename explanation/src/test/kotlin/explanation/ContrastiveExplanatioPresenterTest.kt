package explanation

import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Problems
import explanation.impl.ContrastiveExplanationPresenter
import explanation.impl.QuestionAddOperator
import io.kotest.core.spec.style.AnnotationSpec

class ContrastiveExplanatioPresenterTest : AnnotationSpec() {

    @Test
    fun `Add useless operator (pickA) to the plan pickC in pickC problem`() {
        val q1 = QuestionAddOperator(
            Problems.pickC,
            Plan.of(listOf(pickC))
        )
        val explanation = Explainer.of(Planner.strips(), q1).explain()

        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())
    }

    @Test
    fun `Add useful operator (pickA) to the plan stackAB in stackAB problem`() {
        val q1 = QuestionAddOperator(
            Problems.stackAB,
            Plan.of(listOf(pickA)),
        )
        val explanation = Explainer.of(Planner.strips(), q1).explain()

        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())
    }
}
