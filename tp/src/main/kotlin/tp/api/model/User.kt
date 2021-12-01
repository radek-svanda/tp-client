package tp.api.model

data class User(
  val resourceType: String,
  val kind: String?,
  val id: Int,
  val firstName: String,
  val lastName: String,
  val login: String,
  val fullName: String
)
