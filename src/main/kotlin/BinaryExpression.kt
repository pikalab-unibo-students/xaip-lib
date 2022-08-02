interface BinaryExpression: Expression {
    val expression1: Expression
    val expression2: Expression
    val operand: Operand
}