/**
 * Entity that wraps a logic expression; [Axiom] entity used it to support logic operands application.
 */
interface Expression {
    /**
     * Method to create [BinaryExpression] using [and] as operator.
     * */
    infix fun and(other: Expression): Expression = BinaryExpression.of(this, other, "and")

    /**
     * Method to create [BinaryExpression] using [or] as operator.
     * */
    infix fun or(other: Expression): Expression = BinaryExpression.of(this, other, "or")

    /**
     * Method to create [UnaryExpression] using [not] as operator.
     * */
    operator fun not(): Expression = UnaryExpression.of(this, "not")
}
