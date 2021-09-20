package tp.api.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.junit.Test
import tp.client.xml.ToStringLocalDateModule
import java.time.LocalDate
import kotlin.test.assertEquals

internal class TimeTest {

    private val objectMapper: XmlMapper = XmlMapper.builder()
        .addModules(
            ToStringLocalDateModule()
        )
        .propertyNamingStrategy(PropertyNamingStrategies.UpperCamelCaseStrategy())
        .build()

    @Test
    fun timeSerialization() {
        val time = Time.request(
            spent = 8,
            remain = 0,
            date = LocalDate.parse("2021-01-02"),
            assignable = Assignable.request(
                id = 123
            )
        )
        assertEquals(
            """<Time ResourceType="Time"><Spent>8</Spent><Remain>0</Remain><Date>2021-01-02T00:00:00</Date><Assignable ResourceType="Assignable" Id="123"/></Time>""",
            objectMapper.writeValueAsString(time)
        )
    }

}
