package impl

import Expression
import Operand
import UnaryExpression

internal data class UnaryExpressionImpl(
    override val expression: Expression,
    override val operand: Operand
) : UnaryExpression
