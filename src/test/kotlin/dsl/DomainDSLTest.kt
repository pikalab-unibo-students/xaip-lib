package dsl // ktlint-disable filename

/**
 * Test for DomainDSL cereation.
 */
fun main() {
    domain {
        name = "block_world"
        types {
            +"any"
            +"string"("any")
            +"block"("string")
        }
        predicates {
            +"on"("block", "block")
            +"at"("block", "location")
            +"clear"("block")
        }
        actions {
            "stack" {
                parameters {
                    "X" ofType "block"
                }
                preconditions {
                    +"on"("a", "b")
                    +"on"("a", "b")
                }
                effects {
                    +"at"("X", "floor")
                    // TODO you should guarantee that the "X" variable created
                    //  here is the exact same instance declared in parameters
                    -"handempty"
                }
            }
        }
        axioms {
            // TODO implemnet DSL for axioms too, if you can
        }
    }
}
