package daw.isel.pt.gomoku.controllers.hypermedia


data class Siren(
    val cls: String,
    val properties: Map<String, String>,
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
    val rel: String,
    val href: String
)
data class Link(
    val rel: String,
    val href: String
)
