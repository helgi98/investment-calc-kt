package org.helgi.investment.util

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

private const val CALC_SCALE = 6
private const val CALC_PRECISION = 24
private val CALC_ROUNDING = RoundingMode.HALF_UP

val CALC_CONTEXT = MathContext(CALC_PRECISION, CALC_ROUNDING)

operator fun BigDecimal.plus(other: BigDecimal): BigDecimal =
    this.add(other, CALC_CONTEXT).setScale(CALC_SCALE, CALC_ROUNDING)

operator fun BigDecimal.minus(other: BigDecimal): BigDecimal =
    this.subtract(other, CALC_CONTEXT).setScale(CALC_SCALE, CALC_ROUNDING)

operator fun BigDecimal.times(other: BigDecimal): BigDecimal =
    this.multiply(other, CALC_CONTEXT).setScale(CALC_SCALE, CALC_ROUNDING)

operator fun BigDecimal.div(other: BigDecimal): BigDecimal =
    this.divide(other, CALC_CONTEXT).setScale(CALC_SCALE, CALC_ROUNDING)

operator fun BigDecimal.rem(other: BigDecimal): BigDecimal =
    this.remainder(other, CALC_CONTEXT).setScale(CALC_SCALE, CALC_ROUNDING)

operator fun BigDecimal.unaryMinus(): BigDecimal =
    this.negate().setScale(CALC_SCALE, CALC_ROUNDING)

operator fun BigDecimal.inc(): BigDecimal =
    this.add(BigDecimal.ONE, CALC_CONTEXT).setScale(CALC_SCALE, CALC_ROUNDING)

operator fun BigDecimal.dec(): BigDecimal =
    this.subtract(BigDecimal.ONE, CALC_CONTEXT).setScale(CALC_SCALE, CALC_ROUNDING)


