import impl.PredicateImpl

/**
 * A predicate should be as the fluents in strips
 */
interface Predicate {
    val name: String
    val arguments: List<Type>

    companion object {
        fun of(name: String, arguments: List<Type>): Predicate = PredicateImpl(name, arguments)
    }
}

