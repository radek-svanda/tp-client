package tp.client

import org.koin.core.component.KoinComponent

class Settings : KoinComponent {

    val username: String?
        get() = prop("targetprocess.username")

    private fun <T> prop(key: String): T? = getKoin().getProperty(key)

}
