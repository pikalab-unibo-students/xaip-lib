package impl

import Expression
import Not
import Operand
import UnaryExpression

class UnaryExpressionImpl: UnaryExpression {
    override val expression: Expression
        get() = TODO("Not yet implemented")
    override val operand: Operand
        get() = TODO("Not yet implemented")

    override fun calculate() {
        when (operand){
            is Not->{

            }
        }
    }
}