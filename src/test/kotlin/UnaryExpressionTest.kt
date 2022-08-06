import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Expressions
import resources.TestUtils.Fluents
import resources.TestUtils.Operands

class UnaryExpressionTest : AnnotationSpec() {
    private val unaryExpression = UnaryExpression.of(Fluents.atXArm, Operand.of("not"))

    @Test
    fun testUnaryExpressionCreation() {
        unaryExpression.expression shouldBe Fluents.atXArm
        unaryExpression.operand shouldBe Operands.not
    }

    @Test
    fun testExpressionObjectWorkAsExpected() {
        Expressions.unaryExpressionNotArmEmpty.expression shouldBe Fluents.armEmpty
        Expressions.unaryExpressionNotArmEmpty.operand shouldBe Operands.not
    }

    @Test
    fun testComplexExpressionObjectWorkAsExpected() {
        val complexUnaryExpression = UnaryExpression.of(unaryExpression, Operand.of("not"))
        complexUnaryExpression.expression shouldBe unaryExpression
        complexUnaryExpression.operand shouldBe Operands.not
        (complexUnaryExpression.expression as UnaryExpression).expression shouldBe Fluents.atXArm
        (complexUnaryExpression.expression as UnaryExpression).operand shouldBe Operands.not
    }
}
