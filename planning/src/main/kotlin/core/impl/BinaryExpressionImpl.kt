package core.impl

import core.BinaryExpression
import core.Expression

internal data class BinaryExpressionImpl(
    override val expression1: Expression,
    override val expression2: Expression,
    override val operand: String
) : BinaryExpression
