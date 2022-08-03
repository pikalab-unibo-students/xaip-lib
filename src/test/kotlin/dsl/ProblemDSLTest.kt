package dsl


/**
 * Test for ProblemDSL cereation.
 */
fun main() {
    problem {
        domain = DomainDSL().buildDomain() //Sta cosa è da cambiare ma non sopporto quel giallo
        objects {
            +"blocks" to {//non sono per nulla convinta che sia sensato questo blocco
                +"a"
                +"b"
                +"c"
            }
        }
        initialState {
            //lo devo mettere il tag fluent prima?
            +"at"("a", "floor")
            +"at"("b", "floor")
            +"at"("c", "floor")
            +"clear"("a")
            +"clear"("b")
            +"clear"("c")

        }
        goals {
            +"on"("a", "b")
        }
    }
}