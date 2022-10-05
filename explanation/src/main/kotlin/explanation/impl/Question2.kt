package explanation.impl

import Domain
import Operator
import Plan
import Problem
import explanation.Question

/**
 * Why operator used rather not being used.
 * Forbid operator's usage in the plan
 */
class Question2(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : Question, AbstractQuestion() {
    override var newPredicate = createNewPredicate(focus, "not_done_", true)
    override var newFluent = createNewFluent(focus, newPredicate)
    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)

    override var oldAction =
        findAction(focus, problem.domain.actions)

    override var newAction = createNewAction(oldAction, newFluent, true)

    override var hDomain = buildHdomain()

    override fun buildHdomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHproblem(): Problem = buildHproblem(hDomain, problem, newGroundFluent, null, true)
}

/**
 * dubbio: considero la posizione o no?
 * se considero lo stato posso "semplicemente" ricondurmi a Q4,
 * per cui mi creo un piano senza l'operatore prescelto e vedo se è solvable,
 * se invece lo interpretiamo come un perché ho utilizzato quest'operatore e  basta
 * facciamo tutto questo sbattimento per cercare un piano che non abbia quell'operatore.
 *
 * Ora prefenza personale non collasserei su Q4, però è anche vero che in presenza
 * di più di un operatore uguale, questo cerca piani che non abbiano mai nessuno di questi ed è un  po' tricky
 * cioé semanticamente per me ci può stare che uno dica che in un certo piano non vuole mai un certo operatore.
 * Però per me ci starebbe anche che non lo volesse solo in un determinato punto, ma quel caso può essere catturato da Q4,
 * in sintesi io lascerei il mondo come sta, tu cosa ne pensi?
 */