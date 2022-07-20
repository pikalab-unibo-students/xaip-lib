package resources

import Action
import Axiom
import Domain
import Effect
import Fluent
import FluentBasedGoal
import Goal
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
import io.mockk.every
import io.mockk.mockkClass

object TestUtils {
    fun getRandomInt(min: Int, max: Int): Int = (min..max).random()

    fun getRandomString(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(length) { charset.random() }.joinToString("")
    }

    const val name = "f"
    const val size = 5

    val axioms = arrayOf(Axioms.axiom1)
    val actions = arrayOf(Actions.pick, Actions.stack)
    val variables= arrayOf(Values.W, Values.X, Values.Y, Values.Z)
    val types = arrayOf(Types.blocks, Types.locations, Types.numbers, Types.strings, Types.anything )
    val predicates= arrayOf(Predicates.at, Predicates.on, Predicates.clear, Predicates.armEmpty)
    val objects = arrayOf(Object.of("a"), Object.of("b"), Object.of("c") , Object.of("floor"), Object.of("arm"), Object.of(0), Object.of(1) , Object.of(2))

    object Actions {
        val pick = Action.of(
            name = "pick",
            parameters = mapOf(
                Values.X to Types.blocks
            ),
            preconditions = setOf(Fluents.atXFloor, Fluents.armEmpty, Fluents.clearX),
            effects = setOf(
                Effect.of(Fluents.atXArm),
                Effect.negative(Fluents.atXFloor),
                Effect.negative(Fluents.armEmpty),
                Effect.negative(Fluents.clearX)
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
                Effect.of(Fluents.armEmpty),
                Effect.negative(Fluents.atXArm),
                Effect.negative(Fluents.clearY)
            )
        )
//aggiunto
        val unStack = Action.of(
            name = "unStack",
            parameters = mapOf(
                Values.X to Types.blocks,
                Values.Y to Types.locations
            ),
            preconditions = setOf(Fluents.onXY, Fluents.clearX),
            effects = setOf(
                Effect.of(Fluents.atXFloor),
                Effect.of(Fluents.armEmpty),
                Effect.negative(Fluents.clearY)
            )
        )
    }

    object Axioms {
        val axiom1 = Axiom.of(
            mapOf(Values.Y to Types.blocks, Values.X to Types.blocks) ,//variabili che possono apparire nella regola
            setOf(Fluents.atXArm), //cosa dice della regola
            setOf(Fluents.onXY))//conseguenze sempre vere della regola sopra
        val axiom2 = Axiom.of(
            mapOf(Values.Y to Types.blocks, Values.X to Types.blocks) ,//variabili che possono apparire nella regola
            setOf(Fluents.atYFloor), //cosa dice della regola
            setOf(Fluents.onXY))//conseguenze sempre vere della regola sopra
    }//es XY si muovo sempre assieme-> se Xè sul braccio allora Y è sotto a X

    object Domains {
        val blockWorld = Domain.of(
            name = "block_world",
            predicates = setOf(Predicates.at, Predicates.on, Predicates.armEmpty),
            actions = setOf(Actions.pick, Actions.stack),
            types = setOf(Types.blocks, Types.locations),
            axioms = emptySet()
        )
        val blockWorldConstrained = Domain.of(
            name = "block_world_constrained",
            predicates = setOf(Predicates.at, Predicates.on, Predicates.armEmpty),
            actions = setOf(Actions.pick, Actions.stack),
            types = setOf(Types.blocks, Types.locations),
            axioms = setOf(Axioms.axiom1, Axioms.axiom2)
        )
    }

    object Effects{
        val atXFloor= Effect.of(Fluents.atXFloor,true)
        val armEmpty= Effect.of(Fluents.armEmpty,true)
    }

    object FluentBasedGoals{
        val f1= FluentBasedGoal.of(setOf(Fluents.atAArm))
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

        val atWFloor= Fluent.positive(Predicates.at, Values.W, Values.floor)
        val atWArm = Fluent.positive(Predicates.at, Values.W, Values.arm)

        val atZFloor = Fluent.positive(Predicates.at, Values.Z, Values.floor)
        val armEmpty = Fluent.positive(Predicates.armEmpty)

        val onAB = Fluent.positive(Predicates.on, Values.a, Values.b)
        val onAX = Fluent.positive(Predicates.on, Values.a, Values.W)

        val clearA = Fluent.positive(Predicates.clear, Values.a)
        val clearB = Fluent.positive(Predicates.clear, Values.b)
        val clearC = Fluent.positive(Predicates.clear, Values.c)
        val clearX = Fluent.positive(Predicates.clear, Values.X)
        val clearY = Fluent.positive(Predicates.clear, Values.Y)
        val clearZ = Fluent.positive(Predicates.clear, Values.Z)
        val clearW = Fluent.positive(Predicates.clear, Values.Z)

        val onXY = Fluent.positive(Predicates.on, Values.X, Values.Y)
        val onWZ = Fluent.positive(Predicates.on, Values.W, Values.Z)
        val onZW = Fluent.positive(Predicates.on, Values.Z, Values.W)
    }

    object Goals {
        val atXArmAndAtYFloorAndOnWZ = FluentBasedGoal.of(Fluents.atXArm, Fluents.atYFloor, Fluents.onWZ)
        val onFlooratAandBatCarm=
            FluentBasedGoal.of(Fluents.atCArm, Fluents.atBFloor, Fluents.atAFloor)
        val onAatBandBonFloor= FluentBasedGoal.of(Fluents.atBFloor, Fluents.onAB)
        val onAX= FluentBasedGoal.of(Fluents.onAX)
        val pickX = FluentBasedGoal.of(Fluents.atXArm)
        val pickXfloorY = FluentBasedGoal.of(Fluents.atXArm, Fluents.atYFloor)
        val onXY = FluentBasedGoal.of(Fluents.onXY)
        val onXYatW = FluentBasedGoal.of(Fluents.atWArm, Fluents.onXY)//caso sfigato
    }

    object ObjectSets {
        val all = ObjectSet.of(
            Types.blocks to setOf(Values.a, Values.b, Values.c),
            Types.locations to setOf(Values.floor, Values.arm),
            Types.numbers to setOf(Values.one, Values.two, Values.zero)
        )
    }

    object Plans {
        val emptyPlan = Plan.of(emptyList())
        val dummyPlan= Plan.of(listOf(Actions.pick, Actions.stack))
    }

    object Planners {
        val dummyPlanner= Planner.strips()
        val floorPlanner= Planner.strips().plan(Problems.stackAny)
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
            objects = ObjectSets.all,
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
        val stackXYpickW = Problem.of(
            domain = Domains.blockWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.onXYatW
        )
    }

    object States {
        val initial = State.of(
            Fluents.atAFloor, Fluents.atBFloor, Fluents.atCFloor, Fluents.armEmpty,
            Fluents.clearA, Fluents.clearB, Fluents.clearC
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

    object VariableAssignments{
        val y2x = VariableAssignment.of(Values.Y, Values.X)
        val x2floor = VariableAssignment.of(Values.X, Values.floor)
        val x2arm= VariableAssignment.of(Values.X, Values.arm)

    }

    val type1 = Type.of(name, null)

    val objEmpty = Object.of("")
    val objNotEmpty = Object.of(name)

    val variableNotEmpty = Variable.of(name)

    val predicateEmpty = Predicate.of("", emptyList())
    val predicateNotEmpty = Predicate.of(name, List(size) { type1 })

    val fluentEmpty = Fluent.of( predicateEmpty, false, emptyList())
    var fluentNotEmpty = Fluent.of(predicateNotEmpty, true, List<Value>(size) { variableNotEmpty })

    val effectEmpty = Effect.of(fluentEmpty, false)
    val effectNotEmpty = Effect.of(fluentNotEmpty, true)

    val axiomEmpty = Axiom.of(emptyMap(), emptySet(), emptySet())
    val axiomNotEmpty = Axiom.of(mapOf(variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(fluentNotEmpty))

    val actionEmpty = Action.of("", emptyMap(), emptySet(), emptySet())
    var actionNotEmpty = Action.of(name, mapOf(variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(effectNotEmpty))

    val domainEmpty = Domain.of("", emptySet(), emptySet(), emptySet(), emptySet())
    val domainNotEmpty =
        Domain.of(name, setOf(predicateNotEmpty), setOf(actionNotEmpty), setOf(type1), setOf(axiomNotEmpty))

    val objectSetEmpty = ObjectSet.of(emptyMap())
    val objectSetNotEmpty = ObjectSet.of(mapOf(type1 to setOf(objNotEmpty)))

    val planEmpty = Plan.of(emptyList())
    val planNotEmpty = Plan.of(listOf(actionNotEmpty))

    val state = State.of(setOf(fluentEmpty))

    val substitution = VariableAssignment.of(variableNotEmpty, variableNotEmpty)
}