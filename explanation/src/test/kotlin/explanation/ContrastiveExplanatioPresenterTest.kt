package explanation

import domain.BlockWorldDomain
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.putdownC
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Problems
import explanation.impl.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith

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

    // Replace operator

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

    // Plan proposal

    @Test
    fun `BlockWorld domain test incorrect plan`() {
        val q4 = QuestionPlanProposal(
            Problems.stackAB,
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA))
        )
        val explanation = Explainer.of(Planner.strips(), q4).explain()
        explanation.isPlanValid() shouldBe false
        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).present())
    }

    // Plan satisfiability

    @Test
    fun `BlockWorld plan valid`() {
        val q5 = QuestionPlanSatisfiability(Problems.pickC, Plan.of(listOf(pickC)))
        val explanation = Explainer.of(Planner.strips(), q5).explain()
        println(explanation.toString())
        explanation.isPlanValid() shouldBe true
        println(ContrastiveExplanationPresenter(explanation).present())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
    }

    @Test
    fun `BlockWorld plan not valid`() { // idempotent operators
        val q5 = QuestionPlanSatisfiability(
            Problems.pickC,
            Plan.of(listOf(pickC, putdownC))
        )
        val explanation = Explainer.of(Planner.strips(), q5).explain()
        explanation.isPlanValid() shouldBe false
        println(ContrastiveExplanationPresenter(explanation).present())
        println("------------------------------")
        println(ContrastiveExplanationPresenter(explanation).presentContrastiveExplanation())
    }

    @Test
    fun `Test exception on not ground goal`() {
        val q1 = QuestionAddOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(BlockWorldDomain.Operators.pickB)),
            pickC,
            1
        )

        val explanation = Explainer.of(Planner.strips(), q1).explain()
        val exception = shouldThrow<IllegalArgumentException> {
            ContrastiveExplanationPresenter(explanation).present()
        }
        exception.message shouldStartWith ("Goal must contain only ground fluents")
    }
}
