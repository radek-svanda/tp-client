package tp.api.time

import io.ktor.client.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tp.api.ApiUrlFactory
import tp.api.model.TpResponse
import java.time.LocalDate

class TimeApi : KoinComponent {

    private val httpClient: HttpClient by inject()
    private val apiUrlFactory: ApiUrlFactory by inject()

    suspend fun timesheet(username: String, from: LocalDate, to: LocalDate): TpResponse {
        val url = apiUrlFactory.getUrl(
            "Time",
            mapOf(
                "select" to "{id,description,project,user,spent,date,assignable}",
                "where" to "(User.login%3D'${username}')and(Date>=DateTime.parse('$from'))"
            ),
            2
        )
        return httpClient.get(url)
    }

}
