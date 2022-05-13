/**
 * An effect consists of a conjunctive logical expression ([fluent]],
 * which defines which values should be set to true or false ([isPositive])
 * if an action is applied.
 */
interface Effect : Applicable<Effect> {
    val fluent: Fluent
    val isPositive: Boolean
}
