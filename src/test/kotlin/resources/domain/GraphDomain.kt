package resources.domain

import Object
object GraphDomain {
    object Actions {
        val move = Action.of(
            name = "move",
            parameters = mapOf(
                Values.X to Types.robots,
                Values.Y to Types.locations,
                Values.Z to Types.locations
            ),
            preconditions = setOf(Fluents.connectedYZ, Fluents.atRobotXlocationY),
            effects = setOf(
                Effect.of(Fluents.atRobotXlocationZ),
                Effect.negative(Fluents.atRobotXlocationY)
            )
        )
        val load = Action.of(
            name = "load",
            parameters = mapOf(
                Values.Z to Types.locations,
                Values.Y to Types.containers,
                Values.X to Types.robots
            ),
            preconditions = setOf(
                Fluents.atRobotXlocationZ,
                Fluents.inContainerYlocationZ
            ),
            effects = setOf(
                Effect.of(Fluents.loadedXY),
                Effect.negative(Fluents.inContainerYlocationZ)
            )
        )
        val unload = Action.of(
            name = "unload",
            parameters = mapOf(
                Values.Z to Types.locations,
                Values.Y to Types.containers,
                Values.X to Types.robots
            ),
            preconditions = setOf(
                Fluents.atRobotXlocationZ,
                Fluents.loadedXY
            ),
            effects = setOf(
                Effect.of(Fluents.inContainerYlocationZ),
                Effect.negative(Fluents.loadedXY)
            )
        )
    }

    object Domains {
        val gridWorld = Domain.of(
            name = "grid_world",
            predicates = setOf(
                Predicates.connected,
                Predicates.atLocation,
                Predicates.loaded,
                Predicates.unloaded,
                Predicates.inContainerLocation
            ),
            actions = setOf(
                Actions.move,
                Actions.load,
                Actions.unload
            ),
            types = setOf(
                Types.anything,
                Types.strings,
                Types.locations,
                Types.robots,
                Types.containers
            )
        )
    }

    object Fluents {
        val atRobotlocation1 = Fluent.positive(Predicates.atLocation, Values.r, Values.l1)
        val atRobotlocation2 = Fluent.positive(Predicates.atLocation, Values.r, Values.l2)
        val atRobotlocation3 = Fluent.positive(Predicates.atLocation, Values.r, Values.l3)
        val atRobotlocation4 = Fluent.positive(Predicates.atLocation, Values.r, Values.l4)
        val atRobotlocation5 = Fluent.positive(Predicates.atLocation, Values.r, Values.l5)
        val atRobotlocation6 = Fluent.positive(Predicates.atLocation, Values.r, Values.l6)
        val atRobotlocation7 = Fluent.positive(Predicates.atLocation, Values.r, Values.l7)

        val atRobotXlocationY = Fluent.positive(Predicates.atLocation, Values.X, Values.Y)
        val atRobotXlocationZ = Fluent.positive(Predicates.atLocation, Values.X, Values.Z)
        val atRobotXlocationW = Fluent.positive(Predicates.atLocation, Values.X, Values.W)

        val connectedL1L2 = Fluent.positive(Predicates.connected, Values.l1, Values.l2)
        val connectedL1L3 = Fluent.positive(Predicates.connected, Values.l1, Values.l3)
        val connectedL2L4 = Fluent.positive(Predicates.connected, Values.l2, Values.l4)
        val connectedL3L4 = Fluent.positive(Predicates.connected, Values.l3, Values.l4)
        val connectedL4L5 = Fluent.positive(Predicates.connected, Values.l4, Values.l5)
        val connectedL1L5 = Fluent.positive(Predicates.connected, Values.l1, Values.l5)
        val connectedL2L6 = Fluent.positive(Predicates.connected, Values.l2, Values.l6)
        val connectedL5L6 = Fluent.positive(Predicates.connected, Values.l5, Values.l6)
        val connectedL5L7 = Fluent.positive(Predicates.connected, Values.l4, Values.l7)

        val connectedXY = Fluent.positive(Predicates.connected, Values.X, Values.Y)
        val connectedXZ = Fluent.positive(Predicates.connected, Values.X, Values.Z)
        val connectedXW = Fluent.positive(Predicates.connected, Values.X, Values.W)
        val connectedYW = Fluent.positive(Predicates.connected, Values.Y, Values.W)
        val connectedYZ = Fluent.positive(Predicates.connected, Values.Y, Values.Z)
        val connectedZW = Fluent.positive(Predicates.connected, Values.Z, Values.W)

        val loadedXY = Fluent.positive(Predicates.loaded, Values.X, Values.Y)

        val unloadedX = Fluent.positive(Predicates.unloaded, Values.X)
        val unloadedY = Fluent.positive(Predicates.unloaded, Values.Y)
        val unloadedZ = Fluent.positive(Predicates.unloaded, Values.Z)
        val unloadedW = Fluent.positive(Predicates.unloaded, Values.W)

        val inContainer1location1 = Fluent.positive(Predicates.inContainerLocation, Values.c1, Values.l1)
        val inContainer1location2 = Fluent.positive(Predicates.inContainerLocation, Values.c1, Values.l2)
        val inContainer1location3 = Fluent.positive(Predicates.inContainerLocation, Values.c1, Values.l3)
        val inContainer1location4 = Fluent.positive(Predicates.inContainerLocation, Values.c1, Values.l4)
        val inContainer1location5 = Fluent.positive(Predicates.inContainerLocation, Values.c1, Values.l5)
        val inContainer1location6 = Fluent.positive(Predicates.inContainerLocation, Values.c1, Values.l6)
        val inContainer1location7 = Fluent.positive(Predicates.inContainerLocation, Values.c1, Values.l7)

        val inContainer2location1 = Fluent.positive(Predicates.inContainerLocation, Values.c2, Values.l1)
        val inContainer2location2 = Fluent.positive(Predicates.inContainerLocation, Values.c2, Values.l2)
        val inContainer2location3 = Fluent.positive(Predicates.inContainerLocation, Values.c2, Values.l3)
        val inContainer2location4 = Fluent.positive(Predicates.inContainerLocation, Values.c2, Values.l4)
        val inContainer2location5 = Fluent.positive(Predicates.inContainerLocation, Values.c2, Values.l5)
        val inContainer2location6 = Fluent.positive(Predicates.inContainerLocation, Values.c2, Values.l6)
        val inContainer2location7 = Fluent.positive(Predicates.inContainerLocation, Values.c2, Values.l7)

        val inContainerXlocationY = Fluent.positive(Predicates.inContainerLocation, Values.X, Values.Y)
        val inContainerXlocationZ = Fluent.positive(Predicates.inContainerLocation, Values.X, Values.Z)
        val inContainerXlocationW = Fluent.positive(Predicates.inContainerLocation, Values.X, Values.W)
        val inContainerYlocationX = Fluent.positive(Predicates.inContainerLocation, Values.Y, Values.X)
        val inContainerYlocationZ = Fluent.positive(Predicates.inContainerLocation, Values.Y, Values.Z)
        val inContainerYlocationW = Fluent.positive(Predicates.inContainerLocation, Values.Y, Values.W)
    }

    object Goals {
        val atRobotAtlocation3 = FluentBasedGoal.of(Fluents.atRobotlocation3)
        val inContainerLocation4 = FluentBasedGoal.of(
            Fluents.inContainer1location4
        )
        val atRobotAtlocation3InContainer1Location4 = FluentBasedGoal.of(
            Fluents.atRobotlocation4,
            Fluents.inContainer1location4
        )
        val atRobotAtlocation3InContainer1Location4InContainer2Location7 = FluentBasedGoal.of(
            Fluents.atRobotlocation7,
            Fluents.inContainer1location4,
            Fluents.inContainer2location3
        )
    }

    object ObjectSets {
        val all = ObjectSet.of(
            Types.robots to setOf(Values.r),
            Types.locations to setOf(Values.l1, Values.l2, Values.l3, Values.l4, Values.l5, Values.l6, Values.l7),
            Types.containers to setOf(Values.c1, Values.c2)
        )
    }

    object Problems {
        val robotFromLoc1ToLoc2 = Problem.of(
            domain = Domains.gridWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.atRobotAtlocation3
        )

        val inContainerLocation4 = Problem.of(
            domain = Domains.gridWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.inContainerLocation4
        )

        val robotFromLoc1ToLoc2ContainerFromLocation2ToLocation4 = Problem.of(
            domain = Domains.gridWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.atRobotAtlocation3InContainer1Location4
        )

        val robotFromLoc1ToLoc3Container1FromLoc2ToLoc4Container2FromLoc4ToLoc7 = Problem.of(
            domain = Domains.gridWorld,
            objects = ObjectSets.all,
            initialState = States.initial,
            goal = Goals.atRobotAtlocation3InContainer1Location4InContainer2Location7
        )
    }

    object Predicates {
        val connected = Predicate.of("connected", Types.locations, Types.locations)
        val atLocation = Predicate.of("atLocation", Types.robots, Types.locations)
        val loaded = Predicate.of("loaded", Types.robots, Types.containers)
        val unloaded = Predicate.of("unloaded", Types.robots)
        val inContainerLocation = Predicate.of("inContainerLocation", Types.containers, Types.robots)
    }

    object States {
        val initial = State.of(
            Fluents.atRobotlocation1,
            Fluents.inContainer1location2,
            Fluents.inContainer2location3,
            Fluents.connectedL1L2,
            Fluents.connectedL1L3,
            Fluents.connectedL2L4,
            Fluents.connectedL3L4,
            Fluents.connectedL4L5,
            Fluents.connectedL4L5,
            Fluents.connectedL2L6,
            Fluents.connectedL5L6,
            Fluents.connectedL5L7,
            Fluents.connectedL1L5

        )
    }

    object Types {
        val anything = Type.of("anything")
        val strings = Type.of("strings", anything)
        val locations = Type.of("locations", strings)
        val robots = Type.of("robots", strings)
        val containers = Type.of("containers", strings)
    }

    object Values {
        val r = Object.of("r")

        val c1 = Object.of("c1")
        val c2 = Object.of("c2")

        val l1 = Object.of("l1")
        val l2 = Object.of("l2")
        val l3 = Object.of("l3")
        val l4 = Object.of("l4")
        val l5 = Object.of("l5")
        val l6 = Object.of("l6")
        val l7 = Object.of("l7")

        val W = Variable.of("W")
        val X = Variable.of("X")
        val Y = Variable.of("Y")
        val Z = Variable.of("Z")
    }
}
