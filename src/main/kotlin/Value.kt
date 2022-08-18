/**
 * Generic entity for the [Value] of an [Object].
 * @property isGround: it states if the [Value] contains variables, or not. It is true, if it does and false otherwise.
 */
interface Value : Applicable<Value> {
    val isGround: Boolean
}
