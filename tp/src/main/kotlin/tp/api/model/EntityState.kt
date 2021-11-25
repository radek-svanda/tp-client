package tp.api.model

data class EntityState(
    val resourceType: String,
    val id: Long,
    val name: String,
    val numericPriority: Double
)
