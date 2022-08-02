package impl

import Expression
import Not
import Operand
import UnaryExpression

internal data class UnaryExpressionImpl(
    override val expression: Expression,
    override val operand: Operand
) : UnaryExpression {

    override fun calculate() {
        when (this.operand) {
            is Not -> {
                TODO("Not yet implemented")
            }
        }
    }
}
