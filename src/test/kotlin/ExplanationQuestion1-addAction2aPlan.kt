class `ExplanationQuestion1-addAction2aPlan` {
    // 1.“Why is action A not used in the plan, rather than being used?” //add action to a state

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
}