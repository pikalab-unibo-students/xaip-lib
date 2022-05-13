package impl
import Object
import ObjectSet
import Type

 class ObjectSetImpl(override val map: Map<Type, Set<Object>>) : ObjectSet
