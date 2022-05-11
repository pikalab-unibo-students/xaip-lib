package impl

import Action
import Effect
import Fluent
import Type
import Var

// TODO Chiedi se ha senso tenere tutto come val oppure se dovrebbero essere var
class ActionImpl(
    override val name: String,
    override val parameters: Map<Var, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>): Action