package dsl // ktlint-disable filename
/**
 * Test for ProblemDSL cereation.
 */
// TODO Make this an actual test asseting that the result of problem { .. } is an instance of Problem containing all the information provided via DSL
fun main() {
    problem {
        domain {
            // Sta cosa è da cambiare ma non sopporto quel giallo
            // TODO questo blocco non ha senso: qui vuoi probabilmente già avre un'instanza di domain, non vuoi crearla al volo
        }
        objects {
            // TODO questo blocco non ha senso, confermo
            +"blocks" to { // non sono per nulla convinta che sia sensato questo blocco
                +"a"
                +"b"
                +"c"
            }
        }
        initialState {
            // lo devo mettere il tag fluent prima?
            // TODO scegli tu come modellare. io non lo metterei (rule of thumb: se una cosa va scritta sempre, che non ci sia o che sia concisa)
            +"at"("a", "floor")
            +"at"("b", "floor")
            +"at"("c", "floor")
            +"clear"("a")
            +"clear"("b")
            +"clear"("c")
        }
        goals {
            +"at"("a", "floor")
            +"at"("b", "floor")
        }
    }
}
