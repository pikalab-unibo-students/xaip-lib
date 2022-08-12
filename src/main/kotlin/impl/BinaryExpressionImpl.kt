package impl

import BinaryExpression
import Expression

internal data class BinaryExpressionImpl(
    override val expression1: Expression,
    override val expression2: Expression,
    override val operand: String
) : BinaryExpression
