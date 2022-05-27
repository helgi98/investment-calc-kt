package org.helgi.investment.entity

import javax.persistence.*

@Embeddable
class PortfolioAssetId(val portfolioId: Long, val assetId: Long): java.io.Serializable

@Entity
class PortfolioAsset(
    @EmbeddedId val id: PortfolioAssetId,
    @ManyToOne @JoinColumn(name = "portfolioId", insertable = false, updatable = false) val portfolio: Portfolio,
    @ManyToOne @JoinColumn(name = "assetId", insertable = false, updatable = false) val asset: Asset,
    val weight: Double
) {
    init {
        require(weight > 0 && weight <= 1) {
            "Weight should be in range (0, 1]"
        }
    }
}