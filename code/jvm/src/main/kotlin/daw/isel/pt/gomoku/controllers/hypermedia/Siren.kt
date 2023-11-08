package daw.isel.pt.gomoku.controllers.hypermedia


data class Siren<T>(
    val cls: String,
    val properties: Map<String, String>,
    val actions: List<Action>,
    val entities: List<Entity>,
    val links: List<Link>
) {

}

data class Action(
    val name: String,
    val title: String,
    val method: String,
    val href: String ,
    val fields: List<Field>
    )

data class Field(
    val name: String,
    val type: String,
)

data class Entity(
    val cls: String,
    val rel: List<String>,
    val href: String
)
data class Link(
    val rel: List<String>,
    val href: String
)

fun Any.getProperties(): Map<String, String> {
    val declaredField = this::class.java.declaredFields
    val propertyMap: MutableMap<String, String> = mutableMapOf()
    declaredField.forEach { field ->
        if(field.trySetAccessible()){
            val value = field.get(this)
            if(value != null){
                propertyMap[field.name] = value.toString()
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
