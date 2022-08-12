package impl

import Expression
import UnaryExpression

internal data class UnaryExpressionImpl(
    override val expression: Expression,
    override val operand: String
) : UnaryExpression
