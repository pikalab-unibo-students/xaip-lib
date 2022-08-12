/**
 * Entity that wraps a logic expression; [Axiom] entity used it to support logic operands application.
 */
interface Expression {
    /**
     * */
    infix fun and(other: Expression): Expression = BinaryExpression.of(this, other, "and")

    /**
     * */
    infix fun or(other: Expression): Expression = BinaryExpression.of(this, other, "or")

    /**
     * */
    operator fun not(): Expression = UnaryExpression.of(this, "not")
}
