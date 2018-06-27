package makes.flint.alt.utility

import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.coinListItem.PriceData
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * NumberFormatter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
private const val THRESHOLD_3_DECIMAL = "0.99"
private const val THRESHOLD_4_DECIMAL = "0.099"
private const val THRESHOLD_5_DECIMAL = "0.0099"

object NumberFormatter {

    private var currencyFormatter = makeCurrencyFormatter()
    private var percentageFormatter = makePercentageFormatter()
    private var numberFormatter = makeNumberFormatter()

    fun formatCurrencyAutomaticDigit(numberToFormat: BigDecimal): String {
        val roundingMode = POHSettings.roundingMode
        val roundedNumber = when {
            numberToFormat > PriceData.decimal3Threshold -> numberToFormat.setScale(2, roundingMode)
            numberToFormat > PriceData.decimal4Threshold -> numberToFormat.setScale(3, roundingMode)
            numberToFormat > PriceData.decimal5Threshold -> numberToFormat.setScale(4, roundingMode)
            else -> numberToFormat.setScale(7, roundingMode)
        }
        return formatCurrency(roundedNumber)
    }

    fun formatCurrency(numberToFormat: BigDecimal, maximumDecimals: Int = 8, minimumDecimals: Int = 2): String {
        val number = numberToFormat.toDouble()
        currencyFormatter.minimumFractionDigits = minimumDecimals
        currencyFormatter.maximumFractionDigits = maximumDecimals
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
