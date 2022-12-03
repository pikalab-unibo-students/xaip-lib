package core.impl

import core.Expression
import core.UnaryExpression

internal data class UnaryExpressionImpl(
    override val expression: Expression,
    override val operand: String
) : UnaryExpression
