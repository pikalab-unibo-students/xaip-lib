package core.impl

import core.Object
import core.ObjectSet
import core.Type

internal data class ObjectSetImpl(override var map: Map<Type, Set<Object>>) : ObjectSet
