package impl.dsl

import Domain
import Predicate


class DomainDSL{
    var name: String = "nome farlocco"
    var predicates: List<Predicate> = TODO()


    fun predicates(f: PredicateDSL.() -> Unit) {

    val ps = PredicateDSL()

    ps.f()

    this.predicates = ps.predicates

  }



  fun buildDomain(): Domain {

      var predicates2: List<Predicate>


      fun predicates(f: PredicateDSL.() -> Unit) {

          val ps = PredicateDSL()

          ps.f()

          predicates2 = ps.predicates

      }


      fun buildDomain(): Domain{
          TODO()
      }
      TODO()
  }

}