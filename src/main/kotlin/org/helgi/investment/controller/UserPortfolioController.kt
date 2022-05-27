package org.helgi.investment.controller

import com.fasterxml.jackson.annotation.JsonFormat
import org.helgi.investment.model.PortfolioEvaluationResponseDTO
import org.helgi.investment.model.PortfolioResponseDTO
import org.helgi.investment.service.PortfolioService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.Min
import javax.validation.constraints.Past

@Validated
@RestController
@RequestMapping("/users/me/investment-portfolio")
class UserPortfolioController(val portfolioService: PortfolioService) {
    @GetMapping
    fun getPortfolio(@RequestParam riskLevel: UInt): PortfolioResponseDTO = portfolioService.getPortfolio(riskLevel)

    @GetMapping("/current-value")
    fun getPortfolioEvaluation(
        @JsonFormat(pattern = "yyyy-MM-dd") @Past @RequestParam from: LocalDate,
        @JsonFormat(pattern = "yyyy-MM-dd") @Past @RequestParam to: LocalDate,
        @RequestParam @Min(0) contribution: BigDecimal, @Min(0) @RequestParam riskLevel: UInt
    ): PortfolioEvaluationResponseDTO = portfolioService.evaluatePortfolio(from, to, contribution, riskLevel)
}