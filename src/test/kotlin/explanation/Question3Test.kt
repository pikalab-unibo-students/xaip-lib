package explanation

import Plan
import State
import explanation.impl.Question3
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.Fluents
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.stackAB
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems

class Question3Test : AnnotationSpec() {

    @Test
    fun `Remove pickA from the plan to solve the armNotEmpty problem`() {
        val q3 = Question3(
            Problems.stackZWpickX,
            Plan.of(listOf(pickA, stackAB, pickC)),
            pickC,
            2,
            State.of(
                Fluents.onAB,
                Fluents.clearA,
                Fluents.atCFloor,
                Fluents.clearC,
                Fluents.atDFloor,
                Fluents.clearD,
                Fluents.atBFloor,
                Fluents.armEmpty
            ),
            pickD
        )

        /* Idea:
            fino ad una certa il piano è ok, ma da un certo punto X in avanti deve e
            ssere cambiato;
            riflettendo cosa succederebbe se dopo X venisse applicata l'azione scelta dall'utente.
            1. mi salvo il piano fino al punto dell'azione che devo andare
        Problema: assenza di controlli sul fatto che unìazione si applicabile in uno stato
                (mancanza di gestione delle eccezioni.

    */
        val actionToKeep = q3.plan.actions.subList(
            0,
            q3.focusOn
        ).toMutableList()

        // B.
        val plans = stripsPlanner.plan(q3.buildHproblem()).toSet()
        for (plan in plans) {
            val hplan = Plan.of(actionToKeep.also { it.add(q3.focus) }.also { it.addAll(plan.actions) })
            val explanation = Explanation.of(q3.plan, hplan)
            println("explanation $explanation")
            val contrastiveExplanation = Explanation.of(
                q3.plan,
                hplan
            )
            explanation shouldBe contrastiveExplanation
        }
    }
}
