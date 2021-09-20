package tp.client.ktor.feature.xml

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.core.*
import tp.client.xml.ToStringLocalDateModule

interface XmlSerializer {
    fun write(data: Any, contentType: ContentType): OutgoingContent

    fun write(data: Any): OutgoingContent = write(data, ContentType.Application.Xml)

    fun read(type: TypeInfo, body: Input): Any

    class Default(
        private val objectMapper: XmlMapper = XmlMapper.builder()
            .addModules(
                ToStringLocalDateModule()
            )
            .propertyNamingStrategy(PropertyNamingStrategies.UpperCamelCaseStrategy())
            .build()
    ) : XmlSerializer {

        override fun write(data: Any, contentType: ContentType): OutgoingContent {
            return TextContent(objectMapper.writeValueAsString(data), contentType)
        }

        override fun read(type: TypeInfo, body: Input): Any {
            return objectMapper.readValue(body.readText(), type.javaClass)
        }
    }
}
