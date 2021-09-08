package tp.api.model

import java.time.LocalDate

data class TpResponse(
  var next: String,
  var items: List<TimesheetItem>
) {
  fun containsDay(date: LocalDate?): Boolean {
    return if (date == null) {
      false
    } else {
      items.any { it.dateAsDate().isEqual(date) }
    }
  }

  fun getDay(date: LocalDate?): List<TimesheetItem> {
    return if (date == null) {
      emptyList()
    } else {
      items.filter { it.dateAsDate().isEqual(date) }
    }
  }
}

