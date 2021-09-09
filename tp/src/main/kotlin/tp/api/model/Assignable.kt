package tp.api.model

data class Assignable(
  val resourceType: String,
  val id: Long,
  val name: String? = null
) {
    companion object {
        fun request(id: Long, resourceType: String = "Assignable"): Assignable =
            Assignable(
                resourceType = resourceType,
                id = id
            )
    }
}
