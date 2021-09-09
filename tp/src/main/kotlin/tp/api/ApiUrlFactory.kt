package tp.api

import org.koin.core.component.KoinComponent

class ApiUrlFactory : KoinComponent {

    fun getUrl(
        handle: String,
        params: Map<String, String> = mapOf(),
        version: Int = 2
    ): String {
        val base = getKoin().getProperty<String>("targetprocess.api.url")
        val token = getKoin().getProperty<String>("targetprocess.token")

        val query = params.entries.joinToString("&") { "${it.key}=${it.value}" }

        return "${base}/v${version}/${handle}?access_token=${token}&${query}"
    }

}
