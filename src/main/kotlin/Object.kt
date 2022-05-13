//TODO scrivi qualcosa di sensato
interface Object : Value {
    val representation: String
    override fun apply(substitution: Substitution): Object
}