package impl

import Object
import ObjectSet
import Type

internal data class ObjectSetImpl(override val map: Map<Type, Set<Object>>) : ObjectSet
