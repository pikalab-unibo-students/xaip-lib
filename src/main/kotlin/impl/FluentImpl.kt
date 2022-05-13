package impl

import Fluent
import Predicate
import Substitution
import Value

data class FluentImpl(
    override val name: String,
    override val args: List<Value>,
    override val instanceOf: Predicate,
    override val isNegated: Boolean
) : Fluent {
    override val isGround: Boolean
        get() = args.all { it.isGround }
    //Non essendo modellato il fallimento della sostituzione direi che questa è la cosa più sensata(non c'è Substitution.failed())
    override fun match(other: Fluent): Boolean = !mostGeneralUnifier(other).isEmpty()


    override fun mostGeneralUnifier(other: Fluent): Substitution {
        TODO("Not yet implemented")
    }
    /*{
        for( localArg in args){
            for (inputArg in other.args){
                if()
            }
        }
    }

     */

    override fun apply(substitution: Substitution): Fluent =
        copy(args = args.map { it.apply(substitution) })


}