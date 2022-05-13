package impl

import Axiom
import Fluent
import Type
import Var

internal data class AxiomImpl(
    override val parameters: Map<Var, Type>,
    override val context: Set<Fluent>,
    override val implies: Set<Fluent>
):Axiom