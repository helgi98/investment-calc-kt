package org.helgi.investment.model

import java.math.BigDecimal
import java.time.LocalDate

data class StockPrice(val date: LocalDate, val price: BigDecimal)

data class StockPrices(val asset: String, val prices: List<StockPrice>)