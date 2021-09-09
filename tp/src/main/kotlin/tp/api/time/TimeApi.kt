package tp.api.time

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tp.api.ApiUrlFactory
import tp.api.model.Assignable
import tp.api.model.Time
import tp.api.model.TimesheetItem
import tp.api.model.TpResponse
import java.time.LocalDate

class TimeApi : KoinComponent {

    private val httpClient: HttpClient by inject()
    private val apiUrlFactory: ApiUrlFactory by inject()

    suspend fun timesheet(username: String, from: LocalDate, to: LocalDate): TpResponse {
        val dateCondition = "(Date>=DateTime.parse('$from'))and(Date<=DateTime.parse('$to'))"
        val url = apiUrlFactory.getUrl(
            "Time",
            mapOf(
                "select" to "{id,description,project,user,spent,date,assignable}",
                "where" to "(User.login%3D'${username}')and${dateCondition}"
            ),
            2
        )
        return httpClient.get(url)
    }

    suspend fun add(taskId: Long, date: LocalDate, spent: Int, remain: Int = 0): Unit {
        val payload = Time.request(
            spent = spent,
            remain = remain,
            date = date,
            assignable = Assignable.request(taskId)
        )
        val url = apiUrlFactory.getUrl(
            handle = "Time",
            version = 1
        )
        httpClient.post<TimesheetItem>(url) {
            contentType(ContentType.Application.Xml)
            body = payload
        }
    }

}
