package benchmark

import core.Plan
import domain.BlockWorldDomain
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.putdownC
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackAC
import domain.BlockWorldDomain.Operators.stackAD
import explanation.impl.* // ktlint-disable no-wildcard-imports

class GeneralExplanationIncorrectPlan {
    val problem1 = BlockWorldDomain.Problems.stackAB

    val q1correct = QuestionAddOperator(
        problem1,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA
            )
        ),
        stackAC,
        2
    )
    val q2correct = QuestionRemoveOperator(
        problem1,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAC,
                pickD
            )
        ),
        pickD
    )

    val q3corret = QuestionReplaceOperator(
        problem1,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAC
            )
        ),
        stackAD,
        3
    )

    val q4correct = QuestionPlanProposal(
        problem1,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAC
            )
        ),
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAD
            )
        )
    )

    val q5correct = QuestionPlanSatisfiability(
        problem1,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAD
            )
        )
    )
}
