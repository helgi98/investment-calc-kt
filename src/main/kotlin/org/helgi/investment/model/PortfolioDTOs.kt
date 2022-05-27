package org.helgi.investment.model

import java.math.BigDecimal

data class PortfolioAssetDTO(
    val weight: Double,
    val assetName: String
) {
    init {
        require(weight > 0 && weight <= 1) {
            "Weight should be in range (0, 1]"
        }
    }
}

data class PortfolioResponseDTO(
    val riskToleranceLowerBound: UInt,
    val riskToleranceUpperBound: UInt,
    val assets: List<PortfolioAssetDTO>
) {
    init {
        require(riskToleranceUpperBound >= riskToleranceLowerBound) {
            "Upper bound should be greater than or equal to lower bound"
        }
    }
}

data class PortfolioEvaluationResponseDTO(
    val portfolioValue: BigDecimal,
    val contributions: BigDecimal
)