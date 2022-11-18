import core.Operator
import core.Problem
import domain.LogisticDomain
import explanation.Question
import explanation.impl.QuestionReplaceOperator

abstract class AbstractBenchmark(val problem: Problem, length: Int) : Benchmark {
    var plan: MutableList<Operator> = MutableList(length) {
        when (problem.domain.name) {
            "block_world" -> Operator.of(problem.domain.actions.first())
            "logistic_world" -> LogisticDomain.Operators.moveRfromL1toL2
            else -> throw NoSuchElementException("Domain ${problem.domain.name} is not supported")
        }
    }
    val resultsTime by lazy { mutableListOf<Long>() }
    val resultsMemory by lazy { mutableListOf<Long>() }

    protected fun addResult(question: Question, explanationType: String) {
        resultsTime.add(measureTimeMillis(question, explanationType))
        when (question) {
            is QuestionReplaceOperator -> resultsMemory.add(
                measureMemory(
                    QuestionReplaceOperator(
                        question.problem,
                        question.plan,
                        question.focus,
                        question.focusOn,
                        question.inState
                    ),
                    explanationType
                )
            )
            else -> resultsMemory.add(measureMemory(question, explanationType))
        }
    }
}
