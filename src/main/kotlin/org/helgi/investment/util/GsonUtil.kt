package org.helgi.investment.util

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDate

class LocalDateDeserializer : JsonDeserializer<LocalDate?> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate? {
        return LocalDate.parse(
            json!!.asString,
            LOCAL_DATE_FORMATTER
        )
    }
}

class LocalDateSerializer : JsonSerializer<LocalDate?> {
    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src!!.format(LOCAL_DATE_FORMATTER))
    }
}

val GSON: Gson = GsonBuilder()
    .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
    .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
    .create()