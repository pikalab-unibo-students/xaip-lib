import impl.OperatorImpl

interface Operator : Applicable<Operator>, Action {
    val args: List<Value>

    companion object {
        fun of(
            action: Action
        ): Operator = OperatorImpl(
            action.name,
            action.parameters,
            action.preconditions,
            action.effects,
            action.positiveEffects,
            action.negativeEffects,
            action.parameters.keys.toList()
        )
    }
}
