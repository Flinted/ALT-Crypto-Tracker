package makes.flint.poh.data.trackerListItem

import makes.flint.poh.configuration.POHSettings
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.data.interfaces.TrackerAssessable
import makes.flint.poh.utility.NumberFormatter
import java.math.BigDecimal

/**
 * TrackerListItem
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerListItem(internal val name: String, internal val symbol: String, internal val id: String) : TrackerAssessable {

    internal lateinit var transactions: MutableList<TrackerListTransaction>
    internal var associatedCoin: CoinListItem? = null

    fun getSymbolFormatted(): String {
        return "($symbol)"
    }

    fun getNumberOwnedFormatted(): String {
        return NumberFormatter.format(getNumberOwned(), 8)
    }

    private fun getNumberOwned(): BigDecimal {
        val total = transactions.fold(BigDecimal.ZERO) { sum, entry ->
            sum.add(entry.quantity)
        }
        return total
    }

    fun getPercentageChangeFormatted(): String {
        val change = getPercentageChange()
        return NumberFormatter.formatPercentage(change, 2)
    }

    override fun getPercentageChange(): BigDecimal {
        val currentPrice = getDecimalTotal(associatedCoin?.priceData?.priceUSD)
        val purchasePrice = getPurchasePriceTotalAccurate()
        val difference = currentPrice.minus(purchasePrice)
        val movement = difference.divide(purchasePrice, POHSettings.roundingMode)
        return movement.setScale(4, POHSettings.roundingMode)
    }

    internal fun getPurchasePriceTotalAccurate(): BigDecimal {
        return transactions.fold(BigDecimal.ZERO) { sum, entry ->
            sum.add(entry.transactionTotal())
        }
    }

    fun getCurrentValueUSDAccurate(): BigDecimal {
        val currentPrice = associatedCoin?.priceData?.priceUSD
        return getDecimalTotal(currentPrice)
    }

    fun getCurrentValueBTCAccurate(): BigDecimal {
        val currentPrice = associatedCoin?.priceData?.priceBTC
        return getDecimalTotal(currentPrice)
    }

    fun getCurrentValueUSDFormatted(): String {
        val currentPrice = associatedCoin?.priceData?.priceUSD
        val totalPrice = getDecimalTotal(currentPrice).setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(totalPrice, 2)
    }

    fun getCurrentValueBTCFormatted(): String {
        val currentPrice = associatedCoin?.priceData?.priceBTC
        val totalPrice = getDecimalTotal(currentPrice).setScale(8, POHSettings.roundingMode)
        return "B${NumberFormatter.format(totalPrice, 8)}"
    }

    private fun getDecimalTotal(price: BigDecimal?): BigDecimal {
        associatedCoin ?: return BigDecimal.ZERO
        val numberOwned = getNumberOwned()
        return numberOwned.multiply(price)
    }

    fun getCurrentAssetPriceFormatted(): String {
        return associatedCoin?.priceData?.priceUSDFormatted() ?: ""
    }

    fun getDollarCostAverageFormatted(): String {
        val average = getDollarCostAverage()
        return NumberFormatter.formatCurrency(average, 2)
    }

    private fun getDollarCostAverage(): BigDecimal {
        val numberOwned = getNumberOwned()
        val totalCost = getPurchasePriceTotalAccurate()
        val dollarCostAverage = totalCost.divide(numberOwned, POHSettings.roundingMode)
        return dollarCostAverage.setScale(2, POHSettings.roundingMode)
    }
}
