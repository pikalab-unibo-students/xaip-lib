interface State {
    val fluents:Set<Fluent>
    fun apply(action: Action): State
    fun isApplicable(action: Action): Boolean
}
