
import resources.BlockWorldDomain
object DomainGrid {
    object Actions {
        val load = Action.of(
            name = "load",
            parameters = mapOf(
                Values.X to BlockWorldDomain.Types.blocks,
                Values.Y to Types.agents,
                Values.W to Types.locations,
                Values.Z to Types.locations
            ),
            preconditions = setOf(
                Fluents.atXLocationW,
                Fluents.atYLocationZ,
                Fluents.clearY,
                Fluents.adjacentZW
            ),
            effects = setOf(
                Effect.negative(Fluents.atXLocationW),
                Effect.negative(Fluents.clearY),
                Effect.of(Fluents.busyXY)
            )
        )

        val unload = Action.of(
            name = "unload",
            parameters = mapOf(
                Values.X to Types.blocks,
                Values.Y to Types.agents,
                Values.W to Types.locations,
                Values.Z to Types.locations
            ),
            preconditions = setOf(Fluents.busyXY, Fluents.atYLocationZ, Fluents.clearW, Fluents.adjacentZW),
            effects = setOf(
                Effect.of(Fluents.clearY),
                Effect.of(Fluents.clearX),
                Effect.negative(Fluents.clearW)
            )
        )

        val move = Action.of(
            name = "move",
            parameters = mapOf(
                Values.Y to Types.agents,
                Values.W to Types.locations,
                Values.Z to Types.locations
            ),
            preconditions = setOf(Fluents.adjacentZW, Fluents.clearW),
            effects = setOf(Effect.negative(Fluents.clearW))
        )
    }

    object Domains {
        val gridDomain = Domain.of(
            name = "grid_world",
            predicates = setOf(Predicates.at, Predicates.on, Predicates.clear),
            actions = setOf(Actions.load, Actions.move, Actions.unload),
            types = setOf(Types.blocks, Types.strings, Types.locations, Types.anything, Types.agents)
        )
    }

    object Effects {
        val atXFloor = Effect.of(Fluents.atXFloor, true)
        val armEmpty = Effect.of(BlockWorldDomain.Fluents.armEmpty, true)
        val onXY = Effect.of(BlockWorldDomain.Fluents.onXY)
    }

    object Fluents {
        val atXFloor: Fluent = Fluent.positive(Predicates.at, Values.X)

        val busyLocation1 =
            Fluent.positive(Predicates.at, Values.X, Values.W)
        // non sono troppo convinta di questo, più per la location

        val atAgent1Location = Fluent.positive(Predicates.at, Values.agent1, Values.Y)
        val atAgent1Location1 = Fluent.positive(Predicates.at, Values.agent1, Values.loc1)
        val atAgent1Location2 = Fluent.positive(Predicates.at, Values.agent1, Values.loc2)
        val atAgent1Location3 = Fluent.positive(Predicates.at, Values.agent1, Values.loc3)
        val atAgent1Location4 = Fluent.positive(Predicates.at, Values.agent1, Values.loc4)

        val atXLocationY = Fluent.positive(Predicates.at, Values.X, Values.Y)
        val atXLocationZ = Fluent.positive(Predicates.at, Values.X, Values.Z)
        val atXLocationW = Fluent.positive(Predicates.at, Values.X, Values.Y)
        val atYLocationX = Fluent.positive(Predicates.at, Values.Y, Values.X)
        val atYLocationZ = Fluent.positive(Predicates.at, Values.Y, Values.Z)
        val atYLocationW = Fluent.positive(Predicates.at, Values.Y, Values.W)

        val atBox1Location1 = Fluent.positive(Predicates.at, Values.agent1, Values.loc1)
        val atBox1Location2 = Fluent.positive(Predicates.at, Values.agent1, Values.loc2)
        val atBox1Location3 = Fluent.positive(Predicates.at, Values.agent1, Values.loc3)
        val atBox1Location4 = Fluent.positive(Predicates.at, Values.agent1, Values.loc4)

        val busyAgent1 = Fluent.positive(Predicates.on, Values.box1, Values.agent1)
        val busyXY = Fluent.positive(Predicates.on, Values.X, Values.Y)
        // forse qui ci andranno delle variabili al posto di box1 e agent1

        // potenzialmente inutile per ora
        val clearBox1 = Fluent.positive(BlockWorldDomain.Predicates.clear, Values.box1)
        val clearAgent1 = Fluent.positive(BlockWorldDomain.Predicates.clear, Values.agent1)

        val clearLoc1 = Fluent.positive(BlockWorldDomain.Predicates.clear, Values.loc1)
        val clearLoc2 = Fluent.positive(BlockWorldDomain.Predicates.clear, Values.loc2)
        val clearLoc3 = Fluent.positive(BlockWorldDomain.Predicates.clear, Values.loc3)
        val clearLoc4 = Fluent.positive(BlockWorldDomain.Predicates.clear, Values.loc4)

        val clearX = Fluent.positive(Predicates.clear, Values.X)
        val clearY = Fluent.positive(Predicates.clear, Values.Y)
        val clearW = Fluent.positive(Predicates.clear, Values.W)
        val clearZ = Fluent.positive(Predicates.clear, Values.Z)

        val adjacentXY = Fluent.positive(Predicates.adjacent, Values.X, Values.Y)
        val adjacentXW = Fluent.positive(Predicates.adjacent, Values.X, Values.W)
        val adjacentXZ = Fluent.positive(Predicates.adjacent, Values.X, Values.Z)
        val adjacentYW = Fluent.positive(Predicates.adjacent, Values.Y, Values.W)
        val adjacentYZ = Fluent.positive(Predicates.adjacent, Values.Y, Values.Z)
        val adjacentZW = Fluent.positive(Predicates.adjacent, Values.Z, Values.W)
    }
    object Goals {
        val atAgent1Location1Box1Location2 =
            FluentBasedGoal.of(Fluents.atAgent1Location1, Fluents.atBox1Location2)
    }
    object ObjectSets {
        val all = ObjectSet.of(
            Types.blocks to setOf(Values.box1),
            Types.locations to setOf(Values.loc1, Values.loc2, Values.loc3, Values.loc4),
            Types.agents to setOf(Values.agent1)
        )
    }

    object Predicates {
        // at dovrebbe andare bene sia per un oggetto sia per un agente,
        // quindi o sdoppio il predicato o ci metto il supertipo
        val on = Predicate.of("on", Types.blocks, Types.agents)
        val at = Predicate.of("at", Types.strings, Types.locations)
        val agentEmpty = Predicate.of("agent_empty")
        val clear = Predicate.of("clear", BlockWorldDomain.Types.blocks)
        val adjacent = Predicate.of("adjacent", Types.locations, Types.locations)
    }
    object Problems {
        val simpleDestination = Problem.of(
            domain = Domains.gridDomain,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.atAgent1Location1Box1Location2
        )
    }
    object States {
        val initial = State.of(Fluents.atAgent1Location2, Fluents.atBox1Location3)
    }
    object Types {
        val anything = Type.of("anything")
        val strings = Type.of("strings", anything)
        val numbers = Type.of("numbers", anything)
        val blocks = Type.of("blocks", strings)
        val agents = Type.of("agents", strings)
        val locations = Type.of("locations", strings)
    }
    object Values {
        val box1 = Object.of("box1")
        val agent1 = Object.of("agent1")

        val loc1 = Object.of("loc1")
        val loc2 = Object.of("loc2")
        val loc3 = Object.of("loc3")
        val loc4 = Object.of("loc4")

        val W = Variable.of("W")
        val X = Variable.of("X")
        val Y = Variable.of("Y")
        val Z = Variable.of("Z")
    }
}
