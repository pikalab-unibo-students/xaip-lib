import tmp.Var

interface Axiom {
    val parameters: Map<Var, Type>
    val context: Set<Fluent>
    val implies: Set<Fluent>
}
