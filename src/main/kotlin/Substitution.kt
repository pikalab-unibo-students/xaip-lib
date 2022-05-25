interface Substitution : Map<Variable, Value> {
    fun merge(other: Substitution): Substitution
}