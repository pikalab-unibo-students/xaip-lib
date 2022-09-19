import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.buildHproblem
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.stackBA
import resources.domain.BlockWorldDomain.Operators.stackBD
import resources.domain.BlockWorldDomain.Operators.stackCA
import resources.domain.BlockWorldDomain.Operators.stackDB
import resources.domain.BlockWorldDomain.Operators.stackDC
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.States

class ExplanationQuestion3ReplacingActionInaState : AnnotationSpec() {
    // 3. “Why is action A used, rather than action B?” // replacing action in a state
    data class Question3(val actionToAdd: Operator, val problem: Problem, val originalPlan: Plan, val state: State? = null)

    @Test
    fun testQuestion3() {
        var newProblem: Problem
        val question = Question3(
            pickC, // fatta al posto di pickB
            Problems.stackDXA,
            Plan.of(
                listOf(
                    pickB,
                    stackBA,
                    pickD,
                    stackDB
                )
            )
        )

        val newState = question.problem.initialState.apply(pickC).first() // questo sarebbe un punto di scelta

        val hProblem = buildHproblem(question.problem.domain, question.problem, null, newState)

        val hplan = stripsPlanner.plan(hProblem).toSet()

        val explanation = buildExplanation(question.originalPlan, Plan.of(mutableListOf(pickC).also { it.addAll(hplan.first().actions as List<Operator>) }), question.actionToAdd)
        val contrastiveExplanation = ExplanationUtils.ContrastiveExplanation(
            question.originalPlan,
            Plan.of(listOf(pickC, stackCA, pickD, stackDC)), // non c'è pickC perché l'ho già applicata nello stato iniziale, non dovrei comunque rappresentarla?
            question.actionToAdd,
            setOf(pickC, stackCA, stackDC),
            setOf(pickB, stackBA, stackDB),
            setOf(pickD)
        )
        explanation shouldBe contrastiveExplanation
    }

    @Test
    fun testQuestion3Extended() {
        val newProblem: Problem
        val question = Question3(
            pickB,
            Problems.stackCAstackBY,
            Plan.of(
                listOf(
                    pickC,
                    stackCA,
                    pickD,
                    stackBD
                )
            ),
            States.onCAatBfloorDfloor
        )

        if (question.state != null) {
            newProblem = Problem.of(
                domain = question.problem.domain,
                objects = question.problem.objects,
                initialState = question.state, // extended
                goal = question.problem.goal // extended
            )
        } else {
            newProblem = question.problem
        }

        println("new initial state: " + newProblem.initialState)

        val newState = newProblem.initialState.apply(question.actionToAdd).first()
        println("apply: " + question.actionToAdd + " to inital state obtaining: " + newState)

        val hDomain = Domain.of( // domain extended
            name = newProblem.domain.name,
            predicates = newProblem.domain.predicates,
            actions = newProblem.domain.actions,
            types = newProblem.domain.types
        )

        val hProblem = Problem.of( // problem extended
            domain = hDomain,
            objects = newProblem.objects,
            initialState = newState, // extended
            goal = newProblem.goal // extended
        )

        val hplan = stripsPlanner.plan(hProblem).toSet()

        val actionsToKeep = mutableListOf<Operator>()
        var tmpStates: Sequence<State> = emptySequence()
        val tmpState: State = State.of(emptySet())
        for (action in question.originalPlan.actions) {
            actionsToKeep.add(action as Operator)
            tmpStates = tmpState.apply(action)
            // mi serve una chiamata ricorsiva
        }
        buildExplanation(question.originalPlan, hplan.first(), question.actionToAdd)
    }
}
