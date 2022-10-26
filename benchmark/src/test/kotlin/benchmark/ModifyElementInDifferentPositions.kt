package benchmark
import core.Plan
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.putdownA
import domain.BlockWorldDomain.Operators.putdownC
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Operators.stackAC
import domain.BlockWorldDomain.Problems
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionReplaceOperator

/***
 * Comparing performance of different question (Q1-Q2( when asked to
 * perform similar operations: adding/replacing an operator in
 * different positions.
 * */

class ModifyElementInDifferentPositions {
    val problem0 = Problems.pickC
    val problem1 = Problems.stackAB
    val q1lenght1 = QuestionAddOperator(
        problem0,
        Plan.of(emptyList()),
        pickC,
        0
    )

    val q1lenght2 = QuestionAddOperator(
        problem1,
        Plan.of(listOf(pickA)),
        stackAB,
        1
    )

    val q1lenght3 = QuestionAddOperator(
        problem0,
        Plan.of(listOf(pickA, putdownA)),
        pickC,
        2
    )

    val q1lenght4 = QuestionAddOperator(
        problem1,
        Plan.of(listOf(pickC, putdownC, pickA)),
        stackAB,
        1
    )

    val q3lenght1 = QuestionReplaceOperator(
        problem0,
        Plan.of(
            listOf(
                pickA
            )
        ),
        pickC,
        0
    )

    val q3lenght2 = QuestionReplaceOperator(
        problem1,
        Plan.of(
            listOf(
                pickA,
                stackAC
            )
        ),
        stackAB,
        1
    )

    val q3lenght3 = QuestionReplaceOperator(
        problem0,
        Plan.of(
            listOf(
                pickA,
                putdownA,
                pickA
            )
        ),
        pickC,
        2
    )

    val q3lenght4 = QuestionReplaceOperator(
        problem1,
        Plan.of(
            listOf(
                pickC,
                putdownC,
                pickA,
                stackAC
            )
        ),
        stackAB,
        3
    )
}
