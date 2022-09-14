import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.findAction
import resources.ExplanationUtils.newPredicate
import resources.domain.BlockWorldDomain.Operators.unstackCD
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
class `ExplanationQuestion1-addAction2aPlan` : AnnotationSpec(){
    // 1.“Why is action A not used in the plan, rather than being used?” //add action to a state

    @Test
    fun testQuestion1() {
        val plans = stripsPlanner.plan(Problems.stackCB).toSet()
        val question = ExplanationUtils.Question1(
            unstackCD,
            Problems.stackCB,
            plans.first()
        )

        println(
            "Piani disponibili: " +
                stripsPlanner.plan(Problems.stackBC).toSet().size +
                "\n" +
                plans
        )

        val predicate = newPredicate(question.actionToAdd)
        // println("new predicate: $predicate")

        val newFluent = createNewFluent(question.actionToAdd, predicate)
        // println("new fluent: $newFluent")

        val notGroundAction =
            findAction(question.actionToAdd, question.problem.domain.actions)
        val newAction = createNewAction(notGroundAction, newFluent)
        // println("updated action: $newAction")

        val HDomain = Domain.of(
            name = question.problem.domain.name,
            predicates = mutableSetOf(predicate).also { it.addAll(question.problem.domain.predicates) },
            actions = mutableSetOf(newAction).also {
                question.problem.domain.actions.map { oldAction ->
                    if (oldAction.name != newAction.name.filter { char -> char.isLetter() }) it.add(oldAction)
                }
            },
            types = question.problem.domain.types
        )
        // println("HDomain action")

        // for (action in HDomain.actions)
        //    println("  " + action.toString())

        val HProblem = Problem.of(
            domain = HDomain,
            objects = question.problem.objects,
            initialState = question.problem.initialState,
            goal = FluentBasedGoal.of(
                (question.problem.goal as FluentBasedGoal).targets.toMutableSet().also {
                    it.add(newFluent)
                }.toSet()
                /*
                mutableSetOf(newFluent)
                    .also {
                        it.addAll((questionAddActionPlan.problem.goal as FluentBasedGoal).targets)
                    }

                 */
            )
        )

        // println("HProblem " + HProblem.goal)
        // println(HProblem)

        val plan = question.plan
        val Hplan = stripsPlanner.plan(HProblem).first()

        // println("plan:" + plan.actions.toList())
        println("Hplan:" + Hplan.actions.toList())

        buildExplanation(plan, Hplan)
    }
}
