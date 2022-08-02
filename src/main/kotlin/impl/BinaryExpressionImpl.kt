package impl

import And
import BinaryExpression
import Expression
import Operand
import Or

internal data class BinaryExpressionImpl(
    override val expression1: Expression,
    override val expression2: Expression,
    override val operand: Operand
) : BinaryExpression {

    override fun calculate() {
        when (this.operand) {
            is And -> {
                TODO("Not yet implemented")
            }
            is Or -> {
                TODO("Not yet implemented")
            }
        }
    }
}
