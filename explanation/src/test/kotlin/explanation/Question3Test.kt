package explanation

import Plan
import State
import explanation.impl.Question3
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Operators.pickA
import domain.BlockWorldDomain.Operators.pickC
import domain.BlockWorldDomain.Operators.pickD
import domain.BlockWorldDomain.Operators.stackAB
import domain.BlockWorldDomain.Planners.stripsPlanner
import domain.BlockWorldDomain.Problems

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
            fino ad una certa il piano è ok, ma da un certo punto X in avanti deve essere cambiato;
            riflettendo cosa succederebbe se dopo X venisse applicata l'azione scelta dall'utente.
            1. mi salvo il piano fino al punto dell'azione che devo andare
        Problema: assenza di controlli sul fatto che un'azione si applicabile in uno stato
                (mancanza di gestione delle eccezioni).
        */

        // Fatti dello user scegliere quale piano dare in pasto all'explanation
        val hplan = stripsPlanner.plan(q3.buildHproblem()).first()
        val explanation = Explanation.of(q3.plan, hplan, q3)
        println("explanation $explanation")
        val contrastiveExplanation = Explanation.of(
            q3.plan,
            hplan,
            q3
        )
        println(explanation.toString())
        explanation shouldBe contrastiveExplanation
        explanation.isPlanValid() shouldBe true
    }
}
