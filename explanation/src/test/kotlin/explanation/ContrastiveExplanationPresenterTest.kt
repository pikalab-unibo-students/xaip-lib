package explanation

import core.Plan
import core.Planner
import domain.BlockWorldDomain
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.putdownC
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Problems
import domain.LogisticDomain
import explanation.impl.BaseExplanationPresenter
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionPlanProposal
import explanation.impl.QuestionPlanSatisfiability
import explanation.impl.QuestionRemoveOperator
import explanation.impl.QuestionReplaceOperator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith

class ContrastiveExplanationPresenterTest : AnnotationSpec() {
    // Add operator
    @Test
    fun `Add useless operator (pickA) to the plan pickC in pickC problem (incorrect plan)`() {
        val q1 = QuestionAddOperator(
            Problems.pickC,
            Plan.of(listOf(pickC)),
            pickA,
            0
        )
        val explanation = Explainer.of(Planner.strips()).explain(q1)

        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(BaseExplanationPresenter(explanation).present())
    }

    @Test
    fun `Basic Add useless operator (pickA) to the plan pickC in pickC problem (incorrect plan)`() {
        val q1 = QuestionAddOperator(
            LogisticDomain.Problems.basicRobotFromLocation1ToLocation2,
            Plan.of(
                listOf(
                    LogisticDomain.Operators.moveRfromL1toL2,
                    LogisticDomain.Operators.moveRfromL2toL1
                )
            ),
            LogisticDomain.Operators.moveRfromL2toL1,
            1
        )

        val explanation = Explainer.of(Planner.strips()).explain(q1)

        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(BaseExplanationPresenter(explanation).present())
    }

    @Test
    fun `Add pickA to the plan stackAB in stackAB problem (correct plan)`() {
        val q1 = QuestionAddOperator(
            Problems.stackAB,
            Plan.of(listOf(stackAB)),
            pickA,
            0
        )
        val explanation = Explainer.of(Planner.strips()).explain(q1)

        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter.of(explanation).present())
    }

    // Remove operator
    @Test
    fun `Remove pickA from the plan to solve the armNotEmpty problem`() {
        val q2 = QuestionRemoveOperator(
            Problems.pickC,
            Plan.of(listOf(pickA, pickC)),
            pickA
        )
        val explanation = Explainer.of(Planner.strips()).explain(q2)
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter.of(explanation).present())
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
        val explanation = Explainer.of(Planner.strips()).explain(q3)
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter.of(explanation).present())
    }

    // Plan proposal

    @Test
    fun `BlockWorld domain test incorrect plan`() {
        val q4 = QuestionPlanProposal(
            Problems.stackAB,
            Plan.of(listOf(pickA, stackAB)),
            Plan.of(listOf(pickA))
        )
        val explanation = Explainer.of(Planner.strips()).explain(q4)
        explanation.isPlanValid() shouldBe false
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
        println("------------------------------")
        println(ContrastiveExplanationPresenter.of(explanation).present())
    }

    // Plan satisfiability

    @Test
    fun `BlockWorld plan valid`() {
        val q5 = QuestionPlanSatisfiability(Problems.pickC, Plan.of(listOf(pickC)))
        val explanation = Explainer.of(Planner.strips()).explain(q5)
        println(explanation.toString())
        explanation.isPlanValid() shouldBe true
        println(ContrastiveExplanationPresenter.of(explanation).present())
        println("------------------------------")
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
    }

    @Test
    fun `BlockWorld plan not valid`() { // idempotent operators
        val q5 = QuestionPlanSatisfiability(
            Problems.pickC,
            Plan.of(listOf(pickC, putdownC))
        )
        val explanation = Explainer.of(Planner.strips()).explain(q5)
        explanation.isPlanValid() shouldBe false
        println(ContrastiveExplanationPresenter.of(explanation).present())
        println("------------------------------")
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
    }

    @Test
    fun `Test exception on not ground goal`() {
        val q1 = QuestionAddOperator(
            Problems.armNotEmpty,
            Plan.of(listOf(BlockWorldDomain.Operators.pickB)),
            pickC,
            1
        )

        val explanation = Explainer.of(Planner.strips()).explain(q1)
        val exception = shouldThrow<IllegalArgumentException> {
            ContrastiveExplanationPresenter.of(explanation).present()
        }
        exception.message shouldStartWith ("Goal must contain only ground fluents")
    }
}
