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
                preconditions {
                    +"on"("a", "b")
                    +"on"("a", "b")
                }
                parameters {
                    +("X" to "block")
                }
                effects {
                    +"atXFloor"("at"("X", "floor"), "true")
                }
            }
        }
        axioms {
        }
    }


problem {
        domain = DomainDSL().buildDomain()
        objects {
            +"blocks" to {
                +"a"
                +"b"
                +"c"
            }
        }
        initialState {
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

