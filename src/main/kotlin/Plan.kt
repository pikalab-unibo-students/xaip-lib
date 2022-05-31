import impl.PlanImpl

interface Plan {
    var actions: List<Action>

    companion object {
        fun of(actions: List<Action>): Plan = PlanImpl(actions)
    }
}