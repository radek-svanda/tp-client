package tp.client.parser

import java.time.Year
import java.time.YearMonth

class YearMonthParser {

    fun parse(value: String?): YearMonth {
        if (value == null || value == "current") {
            return YearMonth.now()
        }
        val monthNumber = value.toIntOrNull()
        if (monthNumber != null) {
            return YearMonth.of(Year.now().value, monthNumber)
        }
        return YearMonth.parse(value)
    }

}
