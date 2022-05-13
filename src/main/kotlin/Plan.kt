import impl.PlanImpl

interface Plan {// cambiato che il piano estenda una lista di azioni non lo trovo sensato
    var actions: List<Action>
    companion object {
        fun of(actions: List<Action>): Plan = PlanImpl(actions)
    }
}