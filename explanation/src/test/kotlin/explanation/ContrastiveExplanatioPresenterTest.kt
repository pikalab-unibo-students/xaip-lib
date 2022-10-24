package explanation

import domain.BlockWorldDomain
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Problems
import explanation.impl.ContrastiveExplanationPresenter
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionRemoveOperator
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class ContrastiveExplanatioPresenterTest : AnnotationSpec() {
    // Add operator
    @Test
    fun `Add useless operator (pickA) to the plan pickC in pickC problem (incorrect plan)`() {
        val q1 = QuestionAddOperator(
            Problems.pickC,
            Plan.of(listOf(pickC)),
            pickA,
            0
        )
        val explanation = Explainer.of(Planner.strips(), q1).explain()

        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())
    }

    @Test
    fun `Add pickA to the plan stackAB in stackAB problem (correct plan)`() {
        val q1 = QuestionAddOperator(
            Problems.stackAB,
            Plan.of(listOf(stackAB)),
            pickA,
            0
        )
        val explanation = Explainer.of(Planner.strips(), q1).explain()

        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())
    }

    // Remove operator
    @Test
    fun `Remove pickA from the plan to solve the armNotEmpty problem`() {
        val q2 = QuestionRemoveOperator(
            Problems.pickC,
            Plan.of(listOf(pickA, pickC)),
            pickA
        )
        val explanation = Explainer.of(Planner.strips(), q2).explain()
        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())
    }

    @Test
    fun `Replace pickA with pickC in stackAB problem`() {
        val q3 = QuestionReplaceOperator(
            Problems.stackAB,
            Plan.of(listOf(pickC, stackAB)),
            pickA,
            0,
            BlockWorldDomain.States.initial
        )
        val explanation = Explainer.of(Planner.strips(), q3).explain()
        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())
    }
}
