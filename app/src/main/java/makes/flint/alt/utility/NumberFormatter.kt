package makes.flint.alt.utility

import makes.flint.alt.configuration.POHSettings
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * NumberFormatter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
object NumberFormatter {

    private var currencyFormatter = makeCurrencyFormatter()
    private var percentageFormatter = makePercentageFormatter()
    private var numberFormatter = makeNumberFormatter()

    fun formatCurrency(numberToFormat: BigDecimal, maximumDecimals: Int = 8, minimumDecimals: Int = 2): String {
        val number = numberToFormat.toDouble()
        currencyFormatter.minimumFractionDigits = minimumDecimals
        val formatted = currencyFormatter.format(number)
        return formatted
    }

    fun format(numberToFormat: BigDecimal, decimals: Int = 8): String {
        val number = numberToFormat.toDouble()
        numberFormatter.maximumFractionDigits = decimals
        return numberFormatter.format(number)
    }

    fun formatPercentage(numberToFormat: BigDecimal, decimals: Int = 2): String {
        val number = numberToFormat.toDouble()
        percentageFormatter.maximumFractionDigits = decimals
        percentageFormatter.minimumFractionDigits = decimals
        return percentageFormatter.format(number)
    }

    private fun makeNumberFormatter() = NumberFormat.getNumberInstance()

    private fun makeCurrencyFormatter(): NumberFormat {
        val formatter = DecimalFormat.getCurrencyInstance(Locale.US)
        formatter.currency = Currency.getInstance(POHSettings.currency)
        return formatter
    }

    private fun makePercentageFormatter() = NumberFormat.getPercentInstance()

}
