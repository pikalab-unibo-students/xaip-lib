import impl.OperandImpl

/**
 * Entity that wraps a logic operand to be applied to an expression.
 */
interface Operand {
    val name: String

    companion object{
        fun of(
            name: String
        ): Operand = OperandImpl(name)
    }
}
