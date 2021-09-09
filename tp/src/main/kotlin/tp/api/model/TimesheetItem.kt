package tp.api.model

import java.time.LocalDate
import java.time.LocalDateTime

data class TimesheetItem(
  val id: Long,
  val project: Project,
  val user: User,
  val spent: Double,
  val date: LocalDateTime,
  val assignable: Assignable
) {
  fun dateAsDate(): LocalDate =
    LocalDate.of(date.year, date.month, date.dayOfMonth)
}
