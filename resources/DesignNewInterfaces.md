
interface Axiom{
  + parameters: Map<Variable, Type>
  + context: Expression
  + implies: Expression // o qualcosa di simile
}

interface Expression

Expression <|-- BinaryExpression
interface BinaryExpression{
  + val fluent1: Fluent
  + val fluent2: Fluent
  + val operator
}

Expression <|-- UnaryExpression
interface UnaryExpression{
  + val fluent1: Fluent
  + val operator
}

Expression <|-- AtomicExpression
interface AtomicExpression

AtomicFormula  <|-- Fluent %predicates, terms
AtomicFormula  <|-- Variable

interface Operator

Operator  <|-- And
interface And
Operator  <|-- Or
interface Or
Operator  <|-- Not
interface Not




domain {
  name = “Nome del dominio”

  predicates {
    +“on”(t(“block”), t(“block”))
    +“at”(block, location)
    +“armEmpty”()
    +“clear”(t("block"))
  }

  action{//Aggiunto
    "pick"{
      parameters {
      "X" ofType "block" //<variable> ofType <type>
      }
      precondition = +fluents //sta roba non ho idea di come gestirla
      effect = +effects
    }
    stack{
      parameters {
        "X" ofType "block" //<variable> ofType <type>
      }
      precondition = +fluents //sta roba non ho idea di come gestirla
      effect = +effects
    }
  }

  types {
    +”any”
    +”string” {
      superType = “”
    }
    + “block”(“string”)
  }

  axiom{//Aggiunto
  parameters: +(Variable, Type) //sta roba non ho idea di come gestirla
  context= +Expression //Qui aspetto che tu mi dica qualcosa per l'altra parte
  implies= +Expression
  }
}

problem{//TUTTO aggiunto
 domain: //Credo di dover ripetere tutta la solfa dello step precedente, ovviamente ora non lo faccio, ma tu correggimi se sbaglio
 objects: {
    all: {
      (blocks, +value)
      (locations, +value)
      (numbers, +value)
    }
 }
 initialState: (+fluents)
 goal:{
  +fluentBasedGoal: +fluent
  +fluent: +fluent
 }
}



class PredicatesDSL {

val predicates = mutableListOf<Predicate>()



  operator fun Predicate.unaryPlus(): Unit {

    predicates.add(it)

  }



  operator fun String.invoke(varargs types: Type): Predicate =

    Predicate.of(this, *types)

}



class DomainDSL {

  var name: String = “nome farlocco”

  private var predicates: List<Predicate>



  fun predicates(f: PredicatesDSL.() -> Unit) {

    val ps = PredicateDsl()

    ps.f()

    this.predicates = ps.predicates

  }



  fun buildDomain(): Domain

}
