package impl

import Axiom
import Fluent
import Type
import Variable

internal data class AxiomImpl(
    override val parameters: Map<Variable, Type>,
    override val context: Set<Fluent>,
    override val implies: Set<Fluent>
):Axiom