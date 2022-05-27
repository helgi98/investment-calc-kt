package org.helgi.investment.integration

import org.helgi.investment.util.GSON
import mu.KotlinLogging
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigDecimal
import java.time.LocalDate


data class FMPStockPrice(val date: LocalDate, val close: BigDecimal)

data class FMPStockPriceResponse(val symbol: String, val historical: List<FMPStockPrice>)

interface FMPApiService {
    @GET("historical-price-full/{asset}?serietype=line")
    suspend fun getStockPrices(
        @Path("asset") assetName: String,
        @Query("from") from: LocalDate,
        @Query("to") to: LocalDate
    ): FMPStockPriceResponse
}

@ConstructorBinding
@ConfigurationProperties(prefix = "integration.fmp")
data class FMPApiConfig(val apiUrl: String, val apiKey: String)

@Component
class FMPApiClient(config: FMPApiConfig) {
    private val logger = KotlinLogging.logger { }
    private val service: FMPApiService

    init {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url().newBuilder()
                    .addQueryParameter("apikey", config.apiKey)
                    .build()
                val request = chain.request().newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(config.apiUrl)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build()

        service = retrofit.create(FMPApiService::class.java)
    }

    suspend fun getStockPrices(assetName: String, from: LocalDate, to: LocalDate): FMPStockPriceResponse {
        try {
            return service.getStockPrices(assetName, from, to)
        } catch (ex: Exception) {
            logger.error { "Failed to fetch stock data. Cause: ${ex.message}" }
            throw Exception("Failed to fetch stock data")
        }
    }
}