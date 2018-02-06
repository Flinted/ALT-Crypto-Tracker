package makes.flint.poh.utility

import makes.flint.poh.configuration.POHSettings
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

/**
 * NumberFormatter
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
object NumberFormatter {

    private var currencyFormatter = makeCurrencyFormatter()
    private var percentageFormatter = makePercentageFormatter()
    private var numberFormatter = makeNumberFormatter()

    fun formatCurrency(numberToFormat: BigDecimal, decimals: Int = 8): String {
        val number = numberToFormat.toDouble()
        currencyFormatter.maximumFractionDigits = decimals
        return currencyFormatter.format(number)
    }

    fun format(numberToFormat: BigDecimal, decimals: Int = 8): String {
        val number = numberToFormat.toDouble()
        numberFormatter.maximumFractionDigits = decimals
        return numberFormatter.format(number)
    }

    fun formatPercentage(numberToFormat: BigDecimal, decimals: Int = 2): String {
        val number = numberToFormat.toDouble()
        percentageFormatter.maximumFractionDigits = decimals
        return percentageFormatter.format(number)
    }

    private fun makeNumberFormatter() = NumberFormat.getNumberInstance()

    private fun makeCurrencyFormatter(): NumberFormat {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        formatter.currency = Currency.getInstance(POHSettings.currency)
        return formatter
    }

    private fun makePercentageFormatter() = NumberFormat.getPercentInstance()

}
