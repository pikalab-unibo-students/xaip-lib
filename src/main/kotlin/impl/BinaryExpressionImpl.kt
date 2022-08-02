package impl

import And
import BinaryExpression
import Expression
import Operand
import Or

class BinaryExpressionImpl(
    override val expression1: Expression,
    override val expression2: Expression,
    override val operand: Operand
) :BinaryExpression {
    override fun calculate(): Any={
        when (operand){
            is And->{

            }
            is Or->{

            }
        }
    }
}