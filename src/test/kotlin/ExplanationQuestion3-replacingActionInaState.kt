import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils.Question1
import resources.ExplanationUtils.buildExplanation
import resources.domain.BlockWorldDomain

class `ExplanationQuestion3-replacingActionInaState` : AnnotationSpec() {
    // 3. “Why is action A used, rather than action B?” // replacing action in a state
    data class Question3(val actionToAdd: Operator, val problem: Problem, val plan: Plan, val state: State)

    @Test
    fun testQuestion3() {
        val question = Question1(
            BlockWorldDomain.Operators.pickC, // fatta al posto di stack AC
            BlockWorldDomain.Problems.stackDXA,
            Plan.of(
                listOf(
                    BlockWorldDomain.Operators.pickB,
                    BlockWorldDomain.Operators.stackAB,
                    BlockWorldDomain.Operators.stackDB
                )
            )
        )
        val newState = question.problem.initialState.apply(BlockWorldDomain.Operators.pickC).first()
        val HDomain = Domain.of( // domain extended
            name = question.problem.domain.name,
            predicates = question.problem.domain.predicates,
            actions = question.problem.domain.actions,
            types = question.problem.domain.types
        )

        val HProblem = Problem.of( // problem extended
            domain = HDomain,
            objects = question.problem.objects,
            initialState = newState, // extended
            goal = question.problem.goal // extended
        )

        val plan = question.plan
        val Hplan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).toSet()

        println("plan:" + plan)
        println("Hplan:" + Hplan)

        buildExplanation(plan, Hplan.first())
    }

    @Test
    fun testQuestion3Extended() {

    }
}
