package core

import domain.BlockWorldDomain.Expressions
import domain.BlockWorldDomain.Fluents
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class UnaryExpressionTest : AnnotationSpec() {
    private val unaryExpression = UnaryExpression.of(Fluents.atXArm, "not")

    @Test
    fun testUnaryExpressionCreation() {
        unaryExpression.expression shouldBe Fluents.atXArm
        unaryExpression.operand shouldBe "not"
    }

    @Test
    fun testExpressionObjectWorkAsExpected() {
        Expressions.unaryExpressionNotArmEmpty.expression shouldBe Fluents.armEmpty
        Expressions.unaryExpressionNotArmEmpty.operand shouldBe "not"
    }

    @Test
    fun testComplexExpressionObjectWorkAsExpected() {
        val complexUnaryExpression = UnaryExpression.of(unaryExpression, "not")
        complexUnaryExpression.expression shouldBe unaryExpression
        complexUnaryExpression.operand shouldBe "not"
        (complexUnaryExpression.expression as UnaryExpression).expression shouldBe Fluents.atXArm
        (complexUnaryExpression.expression as UnaryExpression).operand shouldBe "not"
    }
}
