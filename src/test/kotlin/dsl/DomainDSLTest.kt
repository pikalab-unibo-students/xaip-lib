package dsl // ktlint-disable filename

/**
 * Test for DomainDSL cereation.
 */
fun main() {
    /*
    domain {
        name = "block_world"
        types {
            +"any"
            +"string"("any")
            +"block"("string")
        }
        predicates {
            +"on"("block", "block") //mi sono incasinata sta beccando quello sbagliato
            +"at"("block", "location")
            +"clear"("block")
        }
        actions {
            "stack" {
                preconditions {
                    +"on"("a", "b")
                    +"on"("a", "b")
                }
                parameters {
                    +("X" to "block")
                }
                effects {
                    +"atXFloor"("at"("X", "floor"), "true")// sta cosa è sensata rispetto alla definizione di effetto
                }
            }
        }
        axioms {
            // ma la DSL la devo fare anche per questi?
        }
    }

     */
}
