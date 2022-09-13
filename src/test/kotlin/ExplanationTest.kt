import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.findAction
import resources.ExplanationUtils.newPredicated
import resources.domain.BlockWorldDomain

class ExplanationTest : AnnotationSpec() {
    /*
    1.“Why is action A not used in the plan, rather than being used?” //add action to a state
    2.“Why is action A used in the plan, rather than not being used?” //remove a specific grounded action
    3. “Why is action A used, rather than action B?” // replacing action in a state
    “Why is action A used before/after action B (rather than after/before)?”
     */

    private var unstackBA = Operator.of(BlockWorldDomain.Actions.unstack)
        .apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.b))
    private var stackAB = Operator.of(BlockWorldDomain.Actions.stack).apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.a))
    private var stackCA = Operator.of(BlockWorldDomain.Actions.stack).apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.c))
    private var stackCB = Operator.of(BlockWorldDomain.Actions.unstack)
        .apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.c))
    private var stackDB = Operator.of(BlockWorldDomain.Actions.stack).apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.d))
    private var stackDC = Operator.of(BlockWorldDomain.Actions.stack).apply(VariableAssignment.of(BlockWorldDomain.Values.X, BlockWorldDomain.Values.d))

    init {
        unstackBA = unstackBA.apply(VariableAssignment.of(BlockWorldDomain.Values.Y, BlockWorldDomain.Values.a))
        stackAB = stackAB.apply(VariableAssignment.of(BlockWorldDomain.Values.Y, BlockWorldDomain.Values.b))
        stackCA = stackCA.apply(VariableAssignment.of(BlockWorldDomain.Values.Y, BlockWorldDomain.Values.a))
        stackCB = unstackBA.apply(VariableAssignment.of(BlockWorldDomain.Values.Y, BlockWorldDomain.Values.b))
        stackDB = stackDB.apply(VariableAssignment.of(BlockWorldDomain.Values.Y, BlockWorldDomain.Values.b))
        stackDC = stackDC.apply(VariableAssignment.of(BlockWorldDomain.Values.Y, BlockWorldDomain.Values.c))
    }

    @Test
    fun testQuestion1() {
        val plans = BlockWorldDomain.Planners.stripsPlanner.plan(BlockWorldDomain.Problems.stackBC).toSet()
        val questionAddActionPlan = ExplanationUtils.Question1(
            unstackBA,
            BlockWorldDomain.Problems.stackBC,
            plans.first()
        )

        println(
            "Piani disponibili: " +
                BlockWorldDomain.Planners.stripsPlanner.plan(BlockWorldDomain.Problems.stackBC).toSet().size +
                "\n" +
                plans
        )

        val predicate = newPredicated(questionAddActionPlan.actionToAdd)
        // println("new predicate: $predicate")

        val newFluent = createNewFluent(questionAddActionPlan.actionToAdd, predicate)
        // println("new fluent: $newFluent")

        val notGroundAction =
            findAction(questionAddActionPlan.actionToAdd, questionAddActionPlan.problem.domain.actions)
        val newAction = createNewAction(notGroundAction, newFluent)
        // println("updated action: $newAction")

        val HDomain = Domain.of(
            name = questionAddActionPlan.problem.domain.name,
            predicates = mutableSetOf(predicate).also { it.addAll(questionAddActionPlan.problem.domain.predicates) },
            actions = mutableSetOf(newAction).also {
                questionAddActionPlan.problem.domain.actions.map { oldAction ->
                    if (oldAction.name != newAction.name.filter { char -> char.isLetter() }) it.add(oldAction)
                }
            },
            types = questionAddActionPlan.problem.domain.types
        )
        // println("HDomain action")

        // for (action in HDomain.actions)
        //    println("  " + action.toString())

        val HProblem = Problem.of(
            domain = HDomain,
            objects = questionAddActionPlan.problem.objects,
            initialState = questionAddActionPlan.problem.initialState,
            goal = FluentBasedGoal.of(
                (questionAddActionPlan.problem.goal as FluentBasedGoal).targets.toMutableSet().also {
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

        val plan = questionAddActionPlan.plan
        val Hplan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).first()

        // println("plan:" + plan.actions.toList())
        println("Hplan:" + Hplan.actions.toList())

        buildExplanation(plan, Hplan)
    }

    @Test
    fun testQuestion2() {
        val questionAddActionPlan = ExplanationUtils.Question1(
            unstackBA,
            BlockWorldDomain.Problems.stackBC,
            Plan.of(listOf(unstackBA, stackCB))
        )
        /*
        fun createNewPredicated(action: Action): Predicate =
            Predicate.of("not_has_done_" + action.name, action.parameters.values.toList())
         */
        val newPredicate = newPredicated(questionAddActionPlan.actionToAdd)
        println("new predicate: " + newPredicate.name)

        /*
        fun createNewFluent(action: Action, predicate: Predicate): Fluent = // new predicate
            Fluent.positive(predicate, BlockWorldDomain.Values.a, BlockWorldDomain.Values.b)
            // TODO(fixa sta roba con gli argomenti delle azioni)
         */
        val newFluent = createNewFluent(questionAddActionPlan.actionToAdd, newPredicate) // new predicate
        println("new fluent: $newFluent")

        fun createNewAction2(action: Action, fluent: Fluent) = // extend action
            Action.of(
                name = action.name + "'",
                parameters = action.parameters,
                preconditions = action.preconditions,
                effects = mutableSetOf(Effect.positive(fluent)).also { it.addAll(action.effects) }
                // add new predicate as negative effect
            )

        val newAction = createNewAction2(BlockWorldDomain.Actions.unstack, newFluent) // new action
        println("updated action: $newAction")

        val HDomain = Domain.of( // domain extended
            name = questionAddActionPlan.problem.domain.name,
            predicates = mutableSetOf(newPredicate).also { it.addAll(questionAddActionPlan.problem.domain.predicates) },
            actions = mutableSetOf(newAction).also {
                questionAddActionPlan.problem.domain.actions.map { oldAction ->
                    if (oldAction.name != newAction.name.filter { char ->
                        char.isLetter()
                    }
                    ) it.add(oldAction)
                }
            },
            types = questionAddActionPlan.problem.domain.types
        )

        val HProblem = Problem.of( // problem extended
            domain = HDomain,
            objects = questionAddActionPlan.problem.objects,
            initialState = State.of(
                mutableSetOf(newFluent).also { it.addAll(questionAddActionPlan.problem.initialState.fluents) }
            ), // extended
            goal = questionAddActionPlan.problem.goal/*FluentBasedGoal.of(
                mutableSetOf(newFluent).also {
                    it.addAll((questionAddActionPlan.problem.goal as FluentBasedGoal).targets)
                }
                */
            // extended
        )

        val plan = questionAddActionPlan.plan
        val Hplan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).toSet()

        println("plan:" + plan.actions.toList())
        println("Hplan:" + Hplan)

        buildExplanation(plan, Hplan.first())
    }

    @Test
    fun testQuestion3() {
        val questionAddActionPlan = ExplanationUtils.Question1(
            stackCA, //fatta al posto di stack AC
            BlockWorldDomain.Problems.stackDXA,
            Plan.of(listOf(stackAB, stackDB))
        )
        val newState = questionAddActionPlan.problem.initialState.apply(stackCA).first()
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
            goal = questionAddActionPlan.problem.goal// extended
        )

        val plan = questionAddActionPlan.plan
        val Hplan = BlockWorldDomain.Planners.stripsPlanner.plan(HProblem).toSet()

        println("plan:" + plan.actions.toList())
        println("Hplan:" + Hplan)

        buildExplanation(plan, Hplan.first())
    }
}
