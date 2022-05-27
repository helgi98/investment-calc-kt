package org.helgi.investment.service

import org.helgi.investment.NotFoundException
import org.helgi.investment.ValidationException
import org.helgi.investment.model.PortfolioAssetDTO
import org.helgi.investment.model.PortfolioEvaluationResponseDTO
import org.helgi.investment.model.PortfolioResponseDTO
import org.helgi.investment.repository.PortfolioRepo
import org.helgi.investment.repository.StockPricesRepo
import org.helgi.investment.util.div
import org.helgi.investment.util.plus
import org.helgi.investment.util.times
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.ChronoUnit

interface PortfolioService {
    fun getPortfolio(riskLevel: UInt): PortfolioResponseDTO
    fun evaluatePortfolio(
        from: LocalDate, to: LocalDate, monthlyContribution: BigDecimal, riskLevel: UInt
    ): PortfolioEvaluationResponseDTO
}

@Service
class PortfolioServiceImpl(val portfolioRepo: PortfolioRepo, val stockPricesRepo: StockPricesRepo) : PortfolioService {
    override fun getPortfolio(riskLevel: UInt): PortfolioResponseDTO {
        val portfolios = portfolioRepo.findByRiskLevel(riskLevel)
        if (portfolios.isEmpty()) {
            throw NotFoundException("Portfolio not found")
        }

        return portfolios.first().run {
            PortfolioResponseDTO(
                riskLowerBound,
                riskUpperBound,
                assets.map { PortfolioAssetDTO(it.weight, it.asset.name) }
            )
        }
    }

    override fun evaluatePortfolio(
        from: LocalDate,
        to: LocalDate,
        monthlyContribution: BigDecimal,
        riskLevel: UInt
    ): PortfolioEvaluationResponseDTO {
        if (from.isAfter(to)) {
            throw ValidationException("[From] date should not be after [To] date")
        }

        val portfolio = getPortfolio(riskLevel)

        val assetNames = portfolio.assets.map { it.assetName }
        val stockPrices = stockPricesRepo.getStockPrices(assetNames, from, to).associate { stockData ->
            Pair(stockData.asset,
                stockData.prices.groupingBy { it.date.withDayOfMonth(1) }
                    .reduce { _, accumulator, element ->
                        if (accumulator.date.isBefore(element.date)) accumulator else element
                    })
        }

        val portfolioValue = portfolio.assets
            .filter { stockPrices.containsKey(it.assetName) }
            .map {
                val assetContribution = monthlyContribution * BigDecimal(it.weight)
                val shares = stockPrices.getValue(it.assetName).map { (_, priceData) ->
                    assetContribution / priceData.price
                }.reduce { x, y -> x + y }
                val lastPrice = stockPrices.getValue(it.assetName).maxByOrNull { (date, _) -> date }!!.value.price
                shares * lastPrice
            }.reduce {x, y -> x + y}

        val totalContribution = monthlyContribution * BigDecimal(investmentPeriods(from, to))

        return PortfolioEvaluationResponseDTO(portfolioValue, totalContribution)
    }

    fun investmentPeriods(from: LocalDate, to: LocalDate): Long {
        return ChronoUnit.MONTHS.between(from.withDayOfMonth(1), to.withDayOfMonth(1)) + 1
    }
}