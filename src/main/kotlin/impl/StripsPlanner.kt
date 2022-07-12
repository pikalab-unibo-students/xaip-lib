package impl

import Action
import Applicable
import Effect
import Fluent
import FluentBasedGoal
import Plan
import Planner
import Problem
import State
import VariableAssignment
import java.util.*

internal class StripsPlanner : Planner {
    override fun plan(problem: Problem): Sequence<Plan> {
        return plan(
            problem.initialState,
            problem.domain.actions,
            problem.goal as FluentBasedGoal
        )
    }

    private fun Stack<Applicable<*>>.apply(substitution: VariableAssignment){
        val i = listIterator()
        while(i.hasNext()){
            val x= i.next()
            i.set(x.apply(substitution))
        }
    }

    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal
    ): Sequence<Plan> = sequence {
        var currentState = initialState
        val stack = Stack<Applicable<*>>().also { it.push(goal) }
        val plan = mutableListOf<Action>()
        while (stack.isNotEmpty()) {
            val head = stack.peek()
            when {
                head is Fluent -> {
                    if (currentState.fluents.any { it.match(head) }) {//secondo giro entra, terzo giro a (c, arm) non fa match nulla passa ad effect
                        val substitution =
                            currentState.fluents.filter { it.match(head) }.map { it.mostGeneralUnifier(head) }
                        stack.pop()//rimuovo la testa prima di applicare la sostituzione allo stack; primo giro se ne va A
                        stack.apply(substitution.first())
                        //TODO("applica la sostituzione a tutto lo stack")
                    } else {//terzo giro C non fa match con lo stato iniziale (dovrebbe essere a terra ma nel goal è nel braccio), arrivata qui ho solo quello sullo stack
                        val h = Effect.of(head)
                        stack.pop()// la pop la devo fare prima se no rimuovo quello che carico
                        val action = actions.first { a -> a.positiveEffects.any { p -> p.match(h) } }
                        stack.push(action)
                        for(p in action.preconditions){
                            stack.push(p)
                        }
                        val effect = action.positiveEffects.first { p -> p.match(h) }
                        stack.apply(h.mostGeneralUnifier(effect))
                        //TODO("retrieve dell'azione")
                        //TODO("push dell'azione")
                        //TODO("push delle precondizioni dell'azione")
                    }
                }
                (head is FluentBasedGoal) -> {//prima iterazione entra qui goal-> fluent sullo stack
                    stack.pop()//cava il fluentBasedGoal prima di mettere i Fluents
                    for (fluent in head.targets) {
                        stack.push(fluent)//carico i tre fluent sullo stack
                    }
                }
                (head is Action) -> {
                    stack.pop()
                    //if H matches some action name; DOMANDA: azioni sullo stack?
                    currentState = currentState.apply(head).first()
                    plan.add(head)
                    //TODO("applicare l'azione a currentState e aggiornarlo")
                }
                else -> {
                    // do nothing
                }
            }
        }
        yield(Plan.of(plan))
    }
}
