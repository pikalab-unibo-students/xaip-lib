package core

import core.BinaryExpression
import domain.BlockWorldDomain.Expressions
import domain.BlockWorldDomain.Expressions.binaryExpression1
import domain.BlockWorldDomain.Expressions.binaryExpression2
import domain.BlockWorldDomain.Fluents
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class BinaryExpressionTest : AnnotationSpec() {

    @Test
    fun testExpressionObjectWorkAsExpected() {
        binaryExpression1.expression1 shouldBe Fluents.atBFloor
        binaryExpression1.expression2 shouldBe Expressions.unaryExpressionNotAFloor
        binaryExpression1.operand shouldBe "and"

        binaryExpression2.expression1 shouldBe Fluents.atBFloor
        binaryExpression2.expression2 shouldBe Expressions.unaryExpressionNotAFloor
        binaryExpression2.operand shouldBe "or"
    }

    @Test
    fun testInfix() {
        val ex1 = Fluents.atBFloor and Fluents.atXArm
        val ex2 = Fluents.atBFloor or Fluents.atXArm
        (ex1 as BinaryExpression).expression1 shouldBe Fluents.atBFloor
        ex1.expression2 shouldBe Fluents.atXArm
        ex1.operand shouldBe "and"
        (ex2 as BinaryExpression).expression1 shouldBe Fluents.atBFloor
        ex2.expression2 shouldBe Fluents.atXArm
        ex2.operand shouldBe "or"
    }
}
