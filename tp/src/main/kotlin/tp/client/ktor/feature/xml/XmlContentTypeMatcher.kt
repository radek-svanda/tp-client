package tp.client.ktor.feature.xml

import io.ktor.http.*

internal object XmlContentTypeMatcher : ContentTypeMatcher {
    override fun contains(contentType: ContentType): Boolean {
        if (ContentType.Application.Xml.match(contentType)) {
            return true
        }

        val value = contentType.withoutParameters().toString()
        return value.startsWith("application/") && value.endsWith("+xml")
    }
}
