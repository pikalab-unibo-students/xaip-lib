package dsl // ktlint-disable filename

import io.kotest.matchers.shouldBe
import resources.TestUtils.domainDSL

/**
 * Test for DomainDSL cereation.
 */
fun main() {
    val d = domain {
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
                    -"armempty"
                }
            }
        }
        axioms {
            parameters {
                "X" ofType "block"
            }
            context = "clear"("x") and "clear"("x")
            // precondizioni
            implies = "clear"("x") and "clear"("x")
            // postcondizioni
        }
    }
    d shouldBe domainDSL
}
