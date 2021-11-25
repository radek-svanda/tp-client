package tp.api.assignables

import io.ktor.client.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tp.api.ApiUrlFactory
import tp.api.model.Assignable
import tp.api.model.ItemList

class TaskApi : KoinComponent {

    private val httpClient: HttpClient by inject()
    private val apiUrlFactory: ApiUrlFactory by inject()

    suspend fun myTasks(): ItemList<Assignable> {
        val url = apiUrlFactory.getUrl(
            handle = "Assignables",
            params = mapOf(
                "select" to "{id,name,resourceType,description,entitystate,owner,AssignedUser,priority,CreateDate}",
                "filter" to "?AssignedUser.Where(it is Me)",
                "where" to "not(resourceType == 'Availability')"
            )
        )
        return httpClient.get<ItemList<Assignable>>(url)
    }
}
