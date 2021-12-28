package tp.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class Assignable(
    @get:JacksonXmlProperty(isAttribute = true)
    val resourceType: String,
    @get:JacksonXmlProperty(isAttribute = true)
    val id: Long,
    @get:JacksonXmlProperty(isAttribute = true)
    val name: String? = null,

//    val entityState: EntityState? = null,
//    val owner: User? = null,
//    val assignedUser: ItemList<User>? = null

) {
    companion object {
        fun request(id: Long, resourceType: String = "Assignable"): Assignable =
            Assignable(
                resourceType = resourceType,
                id = id
            )
    }
}
