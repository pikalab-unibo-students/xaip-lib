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
            // +"on"("block", "block")
        }
        actions {
            "stack" {
                preconditions {
                    +"on"("a", "b")
                    +"on"("a", "b")
                }
            }
        }
    }
}
