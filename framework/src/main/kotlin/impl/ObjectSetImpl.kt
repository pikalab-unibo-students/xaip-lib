package impl

import Object
import ObjectSet
import Type

internal data class ObjectSetImpl(override var map: Map<Type, Set<Object>>) : ObjectSet
