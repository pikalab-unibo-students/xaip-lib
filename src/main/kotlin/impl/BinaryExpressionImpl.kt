package impl

import BinaryExpression
import Expression
import Operand

internal data class BinaryExpressionImpl(
    override val expression1: Expression,
    override val expression2: Expression,
    override val operand: Operand
) : BinaryExpression
