package org.helgi.investment.config

import org.helgi.investment.util.LOCAL_DATE_FORMATTER
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import java.time.LocalDate
import java.util.*

@Configuration
class SerializationConfig {
    @Bean
    fun localDateFormatter(): Formatter<LocalDate> {
        return object : Formatter<LocalDate> {
            override fun parse(text: String, locale: Locale): LocalDate {
                return LocalDate.parse(text, LOCAL_DATE_FORMATTER)
            }

            override fun print(value: LocalDate, locale: Locale): String {
                return LOCAL_DATE_FORMATTER.format(value)
            }
        }
    }
}