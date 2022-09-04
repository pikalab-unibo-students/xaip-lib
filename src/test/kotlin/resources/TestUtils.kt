package resources

import Action
import Axiom
import Domain
import Effect
import Fluent
import FluentBasedGoal
import Object
import ObjectSet
import Plan
import Planner
import Predicate
import Problem
import State
import Type
import Value
import Variable
import VariableAssignment
import dsl.domain
import dsl.problem

object TestUtils {
    const val name = "F"
    const val size = 5

    val axioms = arrayOf(Axioms.axiom1, Axioms.axiom2)
    val actions = arrayOf(Actions.pick, Actions.stack, Actions.unStack)
    val variables = arrayOf(Values.W, Values.X, Values.Y, Values.Z)
    val types = arrayOf(Types.blocks, Types.locations, Types.numbers, Types.strings, Types.anything)
    val predicates = arrayOf(Predicates.at, Predicates.on, Predicates.clear, Predicates.armEmpty)
    val objects = arrayOf(
        Object.of("a"),
        Object.of("b"),
        Object.of("c"),
        Object.of("floor"),
        Object.of("arm"),
        Object.of(0),
        Object.of(1),
        Object.of(2)
    )
    object DomainsDSL {
        val blockWorldXDomainDSL = domain {
            name = "block_world"
            types {
                +"anything"
                +"strings"("anything")
                +"blocks"("strings")
                +"locations"("strings")
            }
            predicates {
                +"on"("blocks", "blocks")
                +"at"("blocks", "locations")
                +"clear"("blocks")
                +"arm_empty"()
            }
            actions {
                "pick" {
                    parameters {
                        "X" ofType "blocks"
                    }
                    preconditions {
                        +"arm_empty"()
                        +"clear"("X")
                    }
                    effects {
                        +"at"("X", "arm")
                        -"arm_empty"
                        -"at"("X", "floor")
                        -"clear"("X")
                    }
                }
                "stack" {
                    parameters {
                        "X" ofType "blocks"
                        "Y" ofType "locations"
                    }
                    preconditions {
                        +"at"("X", "arm")
                        +"clear"("Y")
                    }
                    effects {
                        +"on"("X", "Y")
                        +"clear"("X")
                        +"arm_empty"
                        -"at"("X", "arm")
                        -"clear"("Y")
                    }
                }
                "unStack" {
                    parameters {
                        "X" ofType "blocks"
                        "Y" ofType "locations"
                    }
                    preconditions {
                        +"on"("X", "Y")
                        +"clear"("X")
                        +"arm_empty"
                    }
                    effects {
                        +"at"("X", "arm")
                        +"clear"("Y")
                        -"arm_empty"
                        -"clear"("X")
                        -"on"("X", "Y")
                    }
                }
            }
            axioms {
                parameters {
                    "X" ofType "blocks"
                    "Y" ofType "locations"
                    "W" ofType "anything"
                    "Z" ofType "strings"
                }
                context = "on"("a", "b") or "on"("a", "c")
                // precondizioni
                implies = !"on"("b", "c")
                // postcondizioni
            }
        }
    }

    object ProblemsDSL {
        val problemOnAB = problem(Domains.blockWorld) {
            objects {
                +"blocks"("a", "b")
            }
            initialState {
                +"at"("a", "floor")
                +"at"("b", "floor")
                +"at"("c", "floor")
                +"arm_empty"()
                +"clear"("a")
                +"clear"("b")
                +"clear"("c")
            }
            goals {
                +"on"("a", "b")
            }
        }
    }

    object Actions {
        val pick = Action.of(
            name = "pick",
            parameters = mapOf(
                Values.X to Types.blocks
            ),
            preconditions = setOf(Fluents.armEmpty, Fluents.clearX),
            effects = setOf(
                Effect.of(Fluents.atXArm),
                Effect.negative(Fluents.armEmpty),
                Effect.negative(Fluents.clearX),
                Effect.negative(Fluents.atXFloor)
            )
        )

        val stack = Action.of(
            name = "stack",
            parameters = mapOf(
                Values.X to Types.blocks,
                Values.Y to Types.locations
            ),
            preconditions = setOf(Fluents.atXArm, Fluents.clearY),
            effects = setOf(
                Effect.of(Fluents.onXY),
                Effect.of(Fluents.clearX),
                Effect.of(Fluents.armEmpty),
                Effect.negative(Fluents.atXArm),
                Effect.negative(Fluents.clearY)
            )
        )

        val unStack = Action.of(
            name = "unStack",
            parameters = mapOf(
                Values.X to Types.blocks,
                Values.Y to Types.locations
            ),
            preconditions = setOf(
                Fluents.onXY,
                Fluents.clearX,
                Fluents.armEmpty
            ),
            effects = setOf(
                Effect.negative(Fluents.clearX),
                Effect.negative(Fluents.onXY),
                Effect.negative(Fluents.armEmpty),
                Effect.of(Fluents.atXArm),
                Effect.of(Fluents.clearY)
            )
        )

        val putdown = Action.of(
            name = "putDown",
            parameters = mapOf(
                Values.X to Types.blocks
            ),
            preconditions = setOf(Fluents.atXArm),
            effects = setOf(
                Effect.negative(Fluents.atXArm),
                Effect.of(Fluents.clearX),
                Effect.of(Fluents.armEmpty),
                Effect.of(Fluents.atXFloor)
            )
        )
    }

    object Expressions {
        val expessionAtArm = Fluents.atAArm
        val unaryExpressionNotArmEmpty = UnaryExpression.of(Fluents.armEmpty, "not")
        val unaryExpressionNotAFloor = UnaryExpression.of(Fluents.atAFloor, "not")
        val binaryExpression1 = BinaryExpression.of(
            Fluents.atBFloor,
            unaryExpressionNotAFloor,
            "and"
        )
        val binaryExpression2 = BinaryExpression.of(
            Fluents.atBFloor,
            unaryExpressionNotAFloor,
            "or"
        )
    }

    object Axioms {
        val axiom1 = Axiom.of(
            mapOf(Values.Y to Types.blocks, Values.X to Types.blocks), // variabili che possono apparire nella regola
            Fluents.atXArm, // cosa dice della regola
            Fluents.onXY
        ) // conseguenze sempre vere della regola sopra
        val axiom2 = Axiom.of(
            mapOf(Values.Y to Types.blocks, Values.X to Types.blocks), // variabili che possono apparire nella regola
            Fluents.atYFloor, // cosa dice della regola
            Fluents.onXY
        ) // conseguenze sempre vere della regola sopra
    } // es XY si muovo sempre assieme-> se Xè sul braccio allora Y è sotto a X

    object Domains {
        val blockWorld = Domain.of(
            name = "block_world",
            predicates = setOf(Predicates.at, Predicates.on, Predicates.armEmpty, Predicates.clear),
            actions = setOf(Actions.pick, Actions.stack),
            types = setOf(Types.blocks, Types.locations, Types.anything, Types.strings)
        )
        val blockWorldAxiomException = Domain.of(
            name = "block_world_axiom_exception",
            predicates = setOf(Predicates.at, Predicates.on, Predicates.armEmpty),
            actions = setOf(Actions.pick, Actions.stack),
            types = setOf(Types.blocks, Types.locations),
            axioms = Axioms.axiom1
        )
    }

    object Effects {
        val atXFloor = Effect.of(Fluents.atXFloor, true)
        val armEmpty = Effect.of(Fluents.armEmpty, true)
        val onXY = Effect.of(Fluents.onXY)
    }

    object Fluents {
        val atAFloor = Fluent.positive(Predicates.at, Values.a, Values.floor)
        val atBFloor = Fluent.positive(Predicates.at, Values.b, Values.floor)
        val atCFloor = Fluent.positive(Predicates.at, Values.c, Values.floor)

        val atAArm = Fluent.positive(Predicates.at, Values.a, Values.arm)
        val atBArm = Fluent.positive(Predicates.at, Values.b, Values.arm)
        val atCArm = Fluent.positive(Predicates.at, Values.c, Values.arm)

        val atXFloor = Fluent.positive(Predicates.at, Values.X, Values.floor)
        val atXArm = Fluent.positive(Predicates.at, Values.X, Values.arm)

        val atYFloor = Fluent.positive(Predicates.at, Values.Y, Values.floor)
        val atYArm = Fluent.positive(Predicates.at, Values.Y, Values.arm)

        val atWFloor = Fluent.positive(Predicates.at, Values.W, Values.floor)
        val atWArm = Fluent.positive(Predicates.at, Values.W, Values.arm)

        val atZFloor = Fluent.positive(Predicates.at, Values.Z, Values.floor)
        val armEmpty = Fluent.positive(Predicates.armEmpty)

        val onAB = Fluent.positive(Predicates.on, Values.a, Values.b)
        val onCA = Fluent.positive(Predicates.on, Values.c, Values.a)
        val onAX = Fluent.positive(Predicates.on, Values.a, Values.W)

        val clearA = Fluent.positive(Predicates.clear, Values.a)
        val clearB = Fluent.positive(Predicates.clear, Values.b)
        val clearC = Fluent.positive(Predicates.clear, Values.c)
        val clearX = Fluent.positive(Predicates.clear, Values.X)
        val clearY = Fluent.positive(Predicates.clear, Values.Y)
        val clearZ = Fluent.positive(Predicates.clear, Values.Z)
        val clearW = Fluent.positive(Predicates.clear, Values.Z)

        val onXY = Fluent.positive(Predicates.on, Values.X, Values.Y)
        val onYX = Fluent.positive(Predicates.on, Values.Y, Values.X)
        val onWX = Fluent.positive(Predicates.on, Values.W, Values.X)
        val onWZ = Fluent.positive(Predicates.on, Values.W, Values.Z)
        val onXW = Fluent.positive(Predicates.on, Values.X, Values.W)
        val onZW = Fluent.positive(Predicates.on, Values.Z, Values.W)
    }

    object Goals {
        val atXArmAndAtYFloorAndOnWZ = FluentBasedGoal.of(Fluents.atXArm, Fluents.atYFloor, Fluents.onWZ)
        val onFlooratAandBatCarm =
            FluentBasedGoal.of(Fluents.atCArm, Fluents.atBFloor, Fluents.atAFloor)
        val onAatBandBonFloor = FluentBasedGoal.of(Fluents.atBFloor, Fluents.onAB)
        val onAX = FluentBasedGoal.of(Fluents.onAX)
        val pickX = FluentBasedGoal.of(Fluents.atXArm)
        val pickXfloorY = FluentBasedGoal.of(Fluents.atXArm, Fluents.atYFloor)
        val onXY = FluentBasedGoal.of(Fluents.onXY)
        val onXYatW = FluentBasedGoal.of(Fluents.atWArm, Fluents.onXY) // caso sfigato
        val onXYW = FluentBasedGoal.of(Fluents.onCA, Fluents.onAB)
    }

    object ObjectSets {
        val all = ObjectSet.of(
            Types.blocks to setOf(Values.a, Values.b, Values.c),
            Types.locations to setOf(Values.floor, Values.arm),
            Types.numbers to setOf(Values.one, Values.two, Values.zero)
        )
        val objects = ObjectSet.of(
            Types.blocks to setOf(Values.a, Values.b, Values.c),
            Types.locations to setOf(Values.floor, Values.arm)
        )
    }

    object Plans {
        val emptyPlan = Plan.of(emptyList())
        val dummyPlan = Plan.of(listOf(Actions.pick, Actions.stack))
    }

    object Planners {
        val dummyPlanner = Planner.strips()
    }

    object Predicates {
        val at = Predicate.of("at", Types.blocks, Types.locations)
        val on = Predicate.of("on", Types.blocks, Types.blocks)
        val armEmpty = Predicate.of("arm_empty")
        val clear = Predicate.of("clear", Types.blocks)
    }

    object Problems {
        val stackAny = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.atXArmAndAtYFloorAndOnWZ
        )

        val stack = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.onFlooratAandBatCarm
        )

        val stackAB = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.objects,
            initialState = States.initial,
            goal = Goals.onAatBandBonFloor
        )

        val stackAX = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.onAX
        )

        val pickX = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.pickX
        )

        val pickXfloorY = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.pickXfloorY
        )

        val stackXY = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.onXY
        )

        val stackABC = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.onXYW
        )

        val stackXYpickW = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.onXYatW
        )

        val axiomException = Problem.of(
            domain = Domains.blockWorldAxiomException,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.onXYatW
        )
    }

    object States {
        val initial = State.of(
            Fluents.atAFloor,
            Fluents.atBFloor,
            Fluents.atCFloor,
            Fluents.armEmpty,
            Fluents.clearA,
            Fluents.clearB,
            Fluents.clearC
        )

        val atAArm = State.of(Fluents.atAArm, Fluents.atBFloor, Fluents.atCFloor, Fluents.clearB, Fluents.clearC)
        val atBArm = State.of(Fluents.atAFloor, Fluents.atBArm, Fluents.atCFloor, Fluents.clearA, Fluents.clearC)
        val atCArm = State.of(Fluents.atAFloor, Fluents.atBFloor, Fluents.atCArm, Fluents.clearA, Fluents.clearB)
    }

    object Types {
        val anything = Type.of("anything")
        val strings = Type.of("strings", anything)
        val numbers = Type.of("numbers", anything)
        val blocks = Type.of("blocks", strings)
        val locations = Type.of("locations", strings)
    }

    object Values {
        val a = Object.of("a")
        val b = Object.of("b")
        val c = Object.of("c")

        val floor = Object.of("floor")
        val arm = Object.of("arm")

        val zero = Object.of(0)
        val one = Object.of(1)
        val two = Object.of(2)

        val W = Variable.of("W")
        val X = Variable.of("X")
        val Y = Variable.of("Y")
        val Z = Variable.of("Z")
    }

    object VariableAssignments {
        val y2x = VariableAssignment.of(Values.Y, Values.X)
        val x2floor = VariableAssignment.of(Values.X, Values.floor)
        val x2arm = VariableAssignment.of(Values.X, Values.arm)
        val x2a = VariableAssignment.of(Values.X, Values.a)
    }

    val type1 = Type.of(name, null)

    val objEmpty = Object.of("")
    val objNotEmpty = Object.of(name)

    val variableNotEmpty = Variable.of(name)

    val predicateEmpty = Predicate.of("", emptyList())
    val predicateNotEmpty = Predicate.of(name, List(size) { type1 })

    val fluentEmpty = Fluent.of(predicateEmpty, false, emptyList())
    var fluentNotEmpty = Fluent.of(predicateNotEmpty, true, List<Value>(size) { variableNotEmpty })

    val effectEmpty = Effect.of(fluentEmpty, false)
    val effectNotEmpty = Effect.of(fluentNotEmpty, true)

    val axiomNotEmpty = Axiom.of(mapOf(variableNotEmpty to type1), fluentNotEmpty, fluentNotEmpty)

    val actionEmpty = Action.of("", emptyMap(), emptySet(), emptySet())
    var actionNotEmpty = Action.of(name, mapOf(variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(effectNotEmpty))

    val domainEmpty = Domain.of("", emptySet(), emptySet(), emptySet())
    val domainNotEmpty =
        Domain.of(name, setOf(predicateNotEmpty), setOf(actionNotEmpty), setOf(type1), axiomNotEmpty)

    val objectSetEmpty = ObjectSet.of(emptyMap())
    val objectSetNotEmpty = ObjectSet.of(mapOf(type1 to setOf(objNotEmpty)))

    val planEmpty = Plan.of(emptyList())
    val planNotEmpty = Plan.of(listOf(actionNotEmpty))

    val substitution = VariableAssignment.of(variableNotEmpty, variableNotEmpty)
}
