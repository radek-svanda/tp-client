package tp.api.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.LocalDate
import java.time.LocalDateTime

data class Time(
    val spent: Int,
    val remain: Int,
    val date: LocalDateTime,
    val assignable: Assignable,
    @get:JacksonXmlProperty(isAttribute = true)
    val resourceType: String = "Time"
) {
    companion object {
        fun request(spent: Int, remain: Int, date: LocalDate, assignable: Assignable): Time {
            return Time(
                spent = spent,
                remain = remain,
                date = date.atStartOfDay(),
                assignable = assignable
            )
        }
    }
}
