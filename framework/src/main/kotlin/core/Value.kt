package core

/**
 * Generic entity for the [Value] of an [Object].
 */
interface Value : Applicable<Value> {
    /**
     *@property isGround: it states if the [Value] contains variables, or not.
     * It is true, if it does and false otherwise.
     */
    val isGround: Boolean
}
