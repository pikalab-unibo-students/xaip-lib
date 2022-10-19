package explanation

interface ExplanationPresenter {
    val explanation: Explanation
    fun present(): String

}
