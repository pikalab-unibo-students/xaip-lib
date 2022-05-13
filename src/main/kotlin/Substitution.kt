interface Substitution : Map<Var, Value> {
    fun merge(other: Substitution): Substitution
}