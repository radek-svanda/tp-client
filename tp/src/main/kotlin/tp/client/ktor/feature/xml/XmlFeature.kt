package tp.client.ktor.feature.xml

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.*

class XmlFeature internal constructor(
    public val serializer: XmlSerializer,
    public val acceptContentTypes: List<ContentType> = listOf(ContentType.Application.Xml),
    private val receiveContentTypeMatchers: List<ContentTypeMatcher> = listOf(XmlContentTypeMatcher)
) {

    internal constructor(config: Config) : this(
        config.serializer,
        config.acceptContentTypes,
        config.receiveContentTypeMatchers
    )

    public class Config {
        public lateinit var serializer: XmlSerializer
        private val _acceptContentTypes: MutableList<ContentType> = mutableListOf(ContentType.Application.Xml)
        private val _receiveContentTypeMatchers: MutableList<ContentTypeMatcher> =
            mutableListOf(XmlContentTypeMatcher)

        public var acceptContentTypes: List<ContentType>
            set(value) {
                require(value.isNotEmpty()) { "At least one content type should be provided to acceptContentTypes" }

                _acceptContentTypes.clear()
                _acceptContentTypes.addAll(value)
            }
            get() = _acceptContentTypes

        public var receiveContentTypeMatchers: List<ContentTypeMatcher>
            set(value) {
                require(value.isNotEmpty()) { "At least one content type should be provided to acceptContentTypes" }
                _receiveContentTypeMatchers.clear()
                _receiveContentTypeMatchers.addAll(value)
            }
            get() = _receiveContentTypeMatchers

        public fun accept(vararg contentTypes: ContentType) {
            _acceptContentTypes += contentTypes
        }

        public fun receive(matcher: ContentTypeMatcher) {
            _receiveContentTypeMatchers += matcher
        }
    }

    internal fun canHandle(contentType: ContentType): Boolean {
        val accepted = acceptContentTypes.any { contentType.match(it) }
        val matchers = receiveContentTypeMatchers

        return accepted || matchers.any { matcher -> matcher.contains(contentType) }
    }

    public companion object Feature : HttpClientFeature<Config, XmlFeature> {
        override val key: AttributeKey<XmlFeature> = AttributeKey("XML")

        override fun install(feature: XmlFeature, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Transform) { payload ->
                feature.acceptContentTypes.forEach { context.accept(it) }

                val contentType = context.contentType() ?: return@intercept
                if (!feature.canHandle(contentType)) return@intercept

                context.headers.remove(HttpHeaders.ContentType)

                val serializedContent = when (payload) {
                    Unit -> EmptyContent
                    is EmptyContent -> EmptyContent
                    else -> feature.serializer.write(payload, contentType)
                }

                proceedWith(serializedContent)
            }

            scope.responsePipeline.intercept(HttpResponsePipeline.Transform) { (info, body) ->
                if (body !is ByteReadChannel) return@intercept

                val contentType = context.response.contentType() ?: return@intercept
                if (!feature.canHandle(contentType)) return@intercept

                val parsedBody = feature.serializer.read(info, body.readRemaining())
                val response = HttpResponseContainer(info, parsedBody)
                proceedWith(response)
            }
        }

        override fun prepare(block: Config.() -> Unit): XmlFeature {
            val config = Config().apply(block)
            val serializer = config.serializer
            val allowedContentTypes = config.acceptContentTypes.toList()
            val receiveContentTypeMatchers = config.receiveContentTypeMatchers
            return XmlFeature(serializer, allowedContentTypes, receiveContentTypeMatchers)
        }
    }
}
