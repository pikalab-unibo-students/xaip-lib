package impl

import Action
import Applicable
import State
import java.util.*

internal data class ChoicePoint(val stack: Stack<Applicable<*>>, val state: State, val plan: MutableList<Action>)
