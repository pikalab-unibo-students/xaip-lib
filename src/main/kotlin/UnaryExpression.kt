import impl.UnaryExpressionImpl

/**
 * Subtype of [Expression] which support operations with logic expression that apply an [Operand] to an [Expression].
 * @property expression: first expression to be used.
 * @property operand: operand to be applied to the expressions.
 */

interface UnaryExpression : Expression {
    val expression: Expression
    val operand: String

    companion object {
        /***
         * Factory method for an [UnaryExpression] creation.
         */
        fun of(
            expression: Expression,
            operand: String
        ): UnaryExpression = UnaryExpressionImpl(expression, operand)
    }
}
