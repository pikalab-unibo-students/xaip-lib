import tmp.Value

interface Fluent {
    val name: String
    val args: List<Value>
    val instanceOf: Predicate
    val isNegated: Boolean
}

