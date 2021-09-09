package tp.client.parser

import java.time.LocalDate

class DateParser {

    fun parse(value: String): LocalDate {
        if (value == "today") {
            return LocalDate.now()
        }
        return LocalDate.parse(value)
    }

}
