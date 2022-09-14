import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils.buildExplanation
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.stackAB
import resources.domain.BlockWorldDomain.Operators.stackBD
import resources.domain.BlockWorldDomain.Operators.stackCA
import resources.domain.BlockWorldDomain.Operators.stackDB
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.States

class ExplanationQuestion3ReplacingActionInaState : AnnotationSpec() {
    // 3. “Why is action A used, rather than action B?” // replacing action in a state
    data class Question3(val actionToAdd: Operator, val problem: Problem, val plan: Plan, val state: State? = null)

    @Test
    fun testQuestion3() {
        var newProblem: Problem
        val question = Question3(
            pickC, // fatta al posto di pickB
            Problems.stackDXA,
            Plan.of(
                listOf(
                    pickB,
                    stackAB,
                    stackDB
                )
            )
        )

        val newState = question.problem.initialState.apply(pickC).first() // questo sarebbe un punto di scelta

        val hDomain = Domain.of( // domain extended
            name = question.problem.domain.name,
            predicates = question.problem.domain.predicates,
            actions = question.problem.domain.actions,
            types = question.problem.domain.types
        )

        val hProblem = Problem.of( // problem extended
            domain = hDomain,
            objects = question.problem.objects,
            initialState = newState, // extended
            goal = question.problem.goal // extended
        )

        val plan = question.plan
        val hplan = stripsPlanner.plan(hProblem).toSet()

        println("plan:" + plan)
        println("Hplan:" + hplan)

        buildExplanation(plan, hplan.first(), question.actionToAdd)
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

        val plan = question.plan
        val hplan = stripsPlanner.plan(hProblem).toSet()

        println("plan:" + plan)
        println("Hplan:" + hplan)

        val actionsToKeep = mutableListOf<Operator>()
        var tmpStates: Sequence<State> = emptySequence()
        val tmpState: State = State.of(emptySet())
        for (action in question.plan.actions) {
            actionsToKeep.add(action as Operator)
            tmpStates = tmpState.apply(action)
            // mi serve una chiamata ricorsiva
        }
        buildExplanation(plan, hplan.first(), question.actionToAdd)
    }
}
