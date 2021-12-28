package tp.client.parser

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DateParser {

    fun parse(value: String): List<LocalDate> {
        return if (value.contains("..")) {
            parseRange(value)
        } else {
            listOf(parseSingle(value))
        }
    }

    private fun parseRange(value: String): List<LocalDate> {
        val parts = value.split("..")
        val start = parseSingle(parts[0])
        val end = parseSingle(parts[1], start)
        return (0..start.until(end, ChronoUnit.DAYS)).map { start.plusDays(it) }
    }

    private fun parseSingle(value: String, base: LocalDate = LocalDate.now()): LocalDate {
        if (value == "today") {
            return base
        }
        if (value.toIntOrNull() != null) {
            return base.withDayOfMonth(value.toInt())
        }
        return LocalDate.parse(value)
    }

}
