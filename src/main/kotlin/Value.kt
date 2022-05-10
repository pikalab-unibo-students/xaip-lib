//TODO scrivi qualcosa di sensato
interface Value

//TODO scopri se sta classe serve davvero
class ValueImpl :Value

//TODO scrivi qualcosa di sensato
interface Var: Value {
    val name: String
}

class VarImpl(override val name: String) : Var

//TODO scrivi qualcosa di sensato
interface Object: Value{
    val representation: String
}

class ObjectImpl(override val representation: String) : Object