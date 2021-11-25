package tp.client.parser

import java.time.LocalDate

class DateParser {

    fun parse(value: String): LocalDate {
        if (value == "today") {
            return LocalDate.now()
        }
        if (value.toIntOrNull() != null) {
            return LocalDate.now().withDayOfMonth(value.toInt())
        }
        return LocalDate.parse(value)
    }

}
