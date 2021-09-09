package tp.client.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateDeserializer : JsonDeserializer<LocalDateTime> {

    companion object {
        fun toLocalDateTime(input: String?): LocalDateTime {
            if (input!!.startsWith("/Date")) {
                val sec = input.substring(6, 6 + "1626386400000".length)
                val offset = input.substring(6 + "1626386400000".length, input.length - 2)
                return LocalDateTime.ofEpochSecond(sec.toLong() / 1000, 0, ZoneOffset.of(offset))
            } else {
                throw IllegalArgumentException("Unknown value ${input}")
            }
        }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime = toLocalDateTime(json?.asString)
}
