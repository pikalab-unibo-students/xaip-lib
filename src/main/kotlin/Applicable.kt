interface Applicable<Self : Applicable<Self>> {
    fun apply(substitution: Substitution): Self
}