package impl

import Domain
import Goal
import ObjectSet
import Problem
import State

internal data class ProblemImpl(
    override val domain: Domain,
    override val objects: ObjectSet,
    override val initialState: State,
    override val goal: Goal
) : Problem