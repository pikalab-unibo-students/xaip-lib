import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils
import resources.domain.BlockWorldDomain

class `ExplanationQuestion3-replacingActionInaState` : AnnotationSpec() {
    // 3. “Why is action A used, rather than action B?” // replacing action in a state

    @Test
    fun testQuestion3() {
        val questionAddActionPlan = ExplanationUtils.Question1(
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
        val newState = questionAddActionPlan.problem.initialState.apply(BlockWorldDomain.Operators.pickC).first()
        val HDomain = Domain.of( // domain extended
            name = questionAddActionPlan.problem.domain.name,
            predicates = questionAddActionPlan.problem.domain.predicates,
            actions = questionAddActionPlan.problem.domain.actions,
            types = questionAddActionPlan.problem.domain.types
        )

        val HProblem = Problem.of( // problem extended
            domain = HDomain,
            objects = questionAddActionPlan.problem.objects,
            initialState = newState, // extended
            goal = questionAddActionPlan.problem.goal // extended
        )

        val plan = questionAddActionPlan.plan
        val Hplan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).toSet()

        println("plan:" + plan)
        println("Hplan:" + Hplan)

        ExplanationUtils.buildExplanation(plan, Hplan.first())
    }
}
