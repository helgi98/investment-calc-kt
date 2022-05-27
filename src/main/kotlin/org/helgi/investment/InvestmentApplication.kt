package org.helgi.investment

import org.helgi.investment.integration.FMPApiConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(FMPApiConfig::class)
class InvestmentApplication

fun main(args: Array<String>) {
	runApplication<InvestmentApplication>(*args)
}
