//TODO teoricamente dovrebbero essere come i fluent descritti in STRIPS
interface Predicate {
    val name: String
    val arguments: List<Type>
}

class PredicateImpl(
    override val name: String,
    override val arguments: List<Type>) : Predicate