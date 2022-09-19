import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils.ContrastiveExplanation
import resources.ExplanationUtils.buildHproblem
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.stackAB
import resources.domain.BlockWorldDomain.Operators.stackBA
import resources.domain.BlockWorldDomain.Operators.stackCA
import resources.domain.BlockWorldDomain.Operators.stackDB
import resources.domain.BlockWorldDomain.Operators.stackDC
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems

class ExplanationQuestion3ReplacingActionInaState : AnnotationSpec() {
    // 3. “Why is action A used, rather than action B?” // replacing action in a state
    data class Question3(val actionToAdd: Operator, val actionToRemove: Operator, val problem: Problem, val originalPlan: Plan, val state: State? = null)

    @Test
    fun testQuestion3() {
        val question = Question3(
            pickC, // fatta al posto di pickB
            pickB,
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

        val explanation = ContrastiveExplanation.of(question.originalPlan, Plan.of(mutableListOf(pickC).also { it.addAll(hplan.first().actions as List<Operator>) }), question.actionToAdd)
        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            Plan.of(listOf(pickC, stackCA, pickD, stackDC)), // non c'è pickC perché l'ho già applicata nello stato iniziale, non dovrei comunque rappresentarla?
            question.actionToAdd
        )
        explanation shouldBe contrastiveExplanation
    }

    @Test
    fun testQuestion3Extended() {
        val newProblem: Problem
        val question = Question3(
            pickD, // al posto di pickC
            pickC,
            Problems.stackZWpickX,
            Plan.of(listOf(pickA, stackAB, pickC)),
            State.of(BlockWorldDomain.Fluents.onAB, BlockWorldDomain.Fluents.clearA, BlockWorldDomain.Fluents.atCFloor, BlockWorldDomain.Fluents.clearC, BlockWorldDomain.Fluents.atDFloor, BlockWorldDomain.Fluents.clearD, BlockWorldDomain.Fluents.atBFloor, BlockWorldDomain.Fluents.armEmpty)
        )

        newProblem = if (question.state != null) {
            Problem.of(
                domain = question.problem.domain,
                objects = question.problem.objects,
                initialState = question.state,
                goal = question.problem.goal
            )
        } else {
            question.problem
        }

        println("new initial state: " + newProblem.initialState)

        val newState = newProblem.initialState.apply(question.actionToAdd).first()
        println("apply: " + question.actionToAdd + " to initial state obtaining: " + newState)

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
        println("problem $hProblem")

        val actionToKeep = question.originalPlan.actions.subList(0, question.originalPlan.actions.indexOf(question.actionToRemove)).toMutableList()
        val plans = stripsPlanner.plan(hProblem).toSet()
        for (plan in plans) {
            val hplan = Plan.of(actionToKeep.also { it.add(question.actionToAdd) }.also { it.addAll(plan.actions) })
            val explanation = ContrastiveExplanation.of(question.originalPlan, hplan, question.actionToAdd)
            println("explanation $explanation")
            val contrastiveExplanation = ContrastiveExplanation.of(
                question.originalPlan,
                hplan,
                question.actionToAdd
            )
            explanation shouldBe contrastiveExplanation
        }
    }
}
