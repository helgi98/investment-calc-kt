package org.helgi.investment.entity

import javax.persistence.*

@Entity
class Portfolio(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val riskLowerBound: UInt,
    val riskUpperBound: UInt,
    @OneToMany(mappedBy = "portfolio") val assets: List<PortfolioAsset>
) {
    init {
        require(riskUpperBound >= riskLowerBound) {
            "Upper bound should be greater than or equal to lower bound"
        }
    }
}