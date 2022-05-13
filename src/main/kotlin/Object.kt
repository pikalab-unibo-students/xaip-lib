import impl.ObjectImpl

//TODO scrivi qualcosa di sensato
interface Object : Value {
    val representation: String
    override fun apply(substitution: Substitution): Object
    /*companion object {
        fun of(representation: String): Object = ObjectImpl()
    }

     */
}