package tp.client.xml

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class ToStringLocalDateModule : SimpleModule() {

    init {
        addSerializer(ToStringLocalDateSerializer())
        addDeserializer(LocalDateTime::class.java, ToStringLocalDateDeserializer())
    }

}

class ToStringLocalDateSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeString(value?.format(formatter) ?: "null")
    }
}

class ToStringLocalDateDeserializer : JsonDeserializer<LocalDateTime>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): LocalDateTime {
        return LocalDateTime.parse(p?.text)
    }
}

private val formatter = DateTimeFormatterBuilder()
    .appendValue(ChronoField.YEAR, 4)
    .appendLiteral("-")
    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
    .appendLiteral("-")
    .appendValue(ChronoField.DAY_OF_MONTH, 2)
    .appendLiteral("T")
    .appendValue(ChronoField.HOUR_OF_DAY, 2)
    .appendLiteral(":")
    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
    .appendLiteral(":")
    .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
    .toFormatter()
