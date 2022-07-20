import impl.PlanImpl

interface Plan {
    val actions: List<Action>

    companion object {
        fun of(actions: List<Action>): Plan = PlanImpl(actions)
    }
}