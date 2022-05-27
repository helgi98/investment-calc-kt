package org.helgi.investment.repository

import org.helgi.investment.integration.FMPApiClient
import org.helgi.investment.model.StockPrice
import org.helgi.investment.model.StockPrices
import org.helgi.investment.util.pmap
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Repository
import java.time.LocalDate

interface StockPricesRepo {
    fun getStockPrices(assets: List<String>, from: LocalDate, to: LocalDate): List<StockPrices>
}

@Repository
class FMPStockPricesRepo(val client: FMPApiClient) : StockPricesRepo {

    override fun getStockPrices(assets: List<String>, from: LocalDate, to: LocalDate): List<StockPrices> {
        return runBlocking {
            assets.pmap { asset ->
                client.getStockPrices(asset, from, to).run {
                    StockPrices(symbol, historical.map { StockPrice(it.date, it.close) })
                }
            }
        }
    }

}