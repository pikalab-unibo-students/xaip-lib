package resources

import Action
import Fluent
import Predicate
import State
import io.mockk.mockk

object res {
    const val nameGC ="Giovanni"
    val fluentEmpty = mockk<Fluent>()
    val predicate= mockk<Predicate>()
    val state= mockk<State>()
    val action = mockk<Action>()
}