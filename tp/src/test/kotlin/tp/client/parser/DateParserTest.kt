package tp.client.parser

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DateParserTest {

    private val parser: DateParser = DateParser()

    @Test
    fun parseToday() {
        assertEquals(listOf(LocalDate.now()), parser.parse("today"))
    }

    @Test
    fun parseDayOfMonth() {
        assertEquals(listOf(LocalDate.now().withDayOfMonth(9)), parser.parse("9"))
    }

    @Test
    fun parseDayMonthYear() {
        assertEquals(listOf(LocalDate.parse("2021-12-28")), parser.parse("2021-12-28"))
    }

    @Test
    fun parseDayRange() {
        assertEquals(
            listOf(
                LocalDate.now().withDayOfMonth(9),
                LocalDate.now().withDayOfMonth(10)
            ),
            parser.parse("9..10")
        )
    }

    @Test
    fun parseDayRangeWithFullDate() {
        assertEquals(
            listOf(
                LocalDate.of(2021, 12, 12),
                LocalDate.of(2021, 12, 13),
                LocalDate.of(2021, 12, 14),
                LocalDate.of(2021, 12, 15),
            ),
            parser.parse("2021-12-12..15")
        )
    }
}
