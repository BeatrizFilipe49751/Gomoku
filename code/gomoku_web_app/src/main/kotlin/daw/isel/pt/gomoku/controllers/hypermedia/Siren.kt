package daw.isel.pt.gomoku.controllers.hypermedia

typealias Properties = Map<String, Any>
data class Siren<T>(
    val cls: String,
    val properties: Properties,
    val actions: List<Action>,
    val entities: List<Entity>,
    val links: List<Link>
)

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


