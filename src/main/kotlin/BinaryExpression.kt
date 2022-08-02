import impl.BinaryExpressionImpl

/**
 * Subtype of [Expression] which support operations with logic expression that apply an [Operand] to two [Expression].
 * @property expression1: first expression to be used.
 * @property expression2: second expression to be used.
 * @property operand: operand to be applied to the expressions.
 */
interface BinaryExpression : Expression {
    val expression1: Expression
    val expression2: Expression
    val operand: Operand

    /**
     * Method responsible for the elaboration of the result of the expression.
     */
    fun calculate(): Any

    companion object {
        /***
         * Factory method for an [BinaryExpression] creation.
         */
        fun of(
            expression1: Expression,
            expression2: Expression,
            operand: Operand
        ): BinaryExpression = BinaryExpressionImpl(expression1, expression2, operand)
    }
}
