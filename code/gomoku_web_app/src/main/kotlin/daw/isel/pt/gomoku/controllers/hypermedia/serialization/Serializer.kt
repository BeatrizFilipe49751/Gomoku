package daw.isel.pt.gomoku.controllers.hypermedia.serialization

import daw.isel.pt.gomoku.controllers.hypermedia.Field
import daw.isel.pt.gomoku.controllers.hypermedia.Properties
import java.util.*


object Serializer {
    fun getProperties(target: Any): Properties {
        val declaredField = target::class.java.declaredFields
        val propertyMap: MutableMap<String, Any> = mutableMapOf()
        declaredField.forEach { field ->
            if(field.trySetAccessible()){
                val value = field.get(target)
                if(value != null){
                    val cls = value::class.java
                    if(isSerializable(cls) || cls.isPrimitive) {
                        propertyMap[field.name] = value
                    } else {
                        val properties = getProperties(value)
                        propertyMap[cls.simpleName.replaceFirstChar { it.lowercase(Locale.getDefault()) }] = properties
                    }

                }
            }
        }
        return propertyMap
    }

    fun getFields(cls: Class<*>): List<Field> {
        val constructor = cls.constructors[0]
        val list = mutableListOf<Field>()
        constructor.parameters.forEach { field ->
            list.add(
                Field(
                    name = field.name,
                    type = field.type.simpleName
                )
            )
        }
        return list
    }
    private fun isSerializable(cls: Class<*>): Boolean {
        return SUPPORTED_TYPES.contains(cls)
    }

    private val SUPPORTED_TYPES get() = setOf(
        Boolean::class.java,
        Char::class.java,
        Byte::class.java,
        Character::class.java,
        Short::class.java,
        Integer::class.java,
        Int::class.java,
        Long::class.java,
        Float::class.java,
        Double::class.java,
        String::class.java,
    )

}
