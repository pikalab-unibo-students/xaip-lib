package impl

import Action
import Effect
import Fluent
import Type
import Var

class ActionImpl(
    override val name: String,
    override val parameters: Map<Var, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>): Action