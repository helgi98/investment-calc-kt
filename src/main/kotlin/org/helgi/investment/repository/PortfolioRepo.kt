package org.helgi.investment.repository

import org.helgi.investment.entity.Portfolio
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PortfolioRepo : CrudRepository<Portfolio, Long> {

    @Query("SELECT p FROM Portfolio p WHERE :riskLevel BETWEEN p.riskLowerBound and p.riskUpperBound")
    fun findByRiskLevel(riskLevel: UInt): List<Portfolio>

}