import impl.UnaryExpressionImpl

/**
 * Subtype of [Expression] which support operations with logic expression that apply an [Operand] to an [Expression].
 * @property expression: first expression to be used.
 * @property operand: operand to be applied to the expressions.
 */

interface UnaryExpression : Expression {
    val expression: Expression
    val operand: Operand

    companion object {
        /***
         * Factory method for an [UnaryExpression] creation.
         */
        fun of(
            expression: Expression,
            operand: Operand
        ): UnaryExpression = UnaryExpressionImpl(expression, operand)
    }
}
