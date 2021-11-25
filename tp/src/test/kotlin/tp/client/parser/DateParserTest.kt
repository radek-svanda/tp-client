package tp.client.parser

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DateParserTest {

    private val parser: DateParser = DateParser()

    @Test
    fun parseToday() {
        assertEquals(LocalDate.now(), parser.parse("today"))
    }

    @Test
    fun parseDayOfMonth() {
        assertEquals(LocalDate.now().withDayOfMonth(9), parser.parse("9"))
    }
}
