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
class TrackerListItem(internal val name: String,
                      internal val symbol: String,
                      internal val id: String,
                      internal val associatedCoin: CoinListItem?,
                      internal var transactions: MutableList<TrackerListTransaction>) : TrackerAssessable {
    internal var numberOwned = getNumberOwned()
    internal var numberOwnedFormatted = makeNumberOwnedFormatted()
    internal var symbolFormatted = makeSymbolFormatted()
    internal var purchasePriceTotal = makePurchasePriceTotalAccurate()
    internal var currentValueUSD = makeCurrentValueUSDAccurate()
    internal var currentPriceUSDFormatted = makeCurrentValueUSDFormatted()
    internal var currentValueBTC = makeCurrentValueBTCAccurate()
    internal var currentPriceBTCFormatted = makeCurrentValueBTCFormatted()
    internal var dollarCostAverage = makeDollarCostAverage()
    internal var dollarCostAverageFormatted = makeDollarCostAverageFormatted()
    override var percentageChange = makePercentageChange()
    internal var percentageChangeFormatted = makePercentageChangeFormatted()

    private fun makeSymbolFormatted(): String {
        return "($symbol)"
    }

    private fun makeNumberOwnedFormatted(): String {
        return NumberFormatter.format(numberOwned, 8)
    }

    private fun getNumberOwned(): BigDecimal {
        val total = transactions.fold(BigDecimal.ZERO) { sum, entry ->
            sum.add(entry.quantity)
        }
        return total
    }

    private fun makePercentageChangeFormatted(): String {
        return NumberFormatter.formatPercentage(percentageChange, 2)
    }

    private fun makePercentageChange(): BigDecimal {
        if (purchasePriceTotal == BigDecimal.ZERO) {
            return BigDecimal.ZERO
        }
        val currentPrice = getDecimalTotal(associatedCoin?.priceData?.priceUSD)
        val purchasePrice = purchasePriceTotal
        val difference = currentPrice.minus(purchasePrice)
        val movement = difference.divide(purchasePrice, POHSettings.roundingMode)
        return movement.setScale(4, POHSettings.roundingMode)
    }

    private fun makePurchasePriceTotalAccurate(): BigDecimal {
        return transactions.fold(BigDecimal.ZERO) { sum, entry ->
            sum.add(entry.transactionTotal())
        }
    }

    private fun makeCurrentValueUSDAccurate(): BigDecimal {
        val currentPrice = associatedCoin?.priceData?.priceUSD
        return getDecimalTotal(currentPrice)
    }

    private fun makeCurrentValueBTCAccurate(): BigDecimal {
        val currentPrice = associatedCoin?.priceData?.priceBTC
        return getDecimalTotal(currentPrice)
    }

    private fun makeCurrentValueUSDFormatted(): String {
        val currentPrice = associatedCoin?.priceData?.priceUSD
        val totalPrice = getDecimalTotal(currentPrice).setScale(2, POHSettings.roundingMode)
        return NumberFormatter.formatCurrency(totalPrice, 2)
    }

    private fun makeCurrentValueBTCFormatted(): String {
        val currentPrice = associatedCoin?.priceData?.priceBTC
        val totalPrice = getDecimalTotal(currentPrice).setScale(8, POHSettings.roundingMode)
        return "B${NumberFormatter.format(totalPrice, 8)}"
    }

    private fun getDecimalTotal(price: BigDecimal?): BigDecimal {
        associatedCoin ?: return BigDecimal.ZERO
        return numberOwned.multiply(price)
    }

    fun getCurrentAssetPriceFormatted(): String {
        return associatedCoin?.priceData?.priceUSDFormatted ?: ""
    }

    private fun makeDollarCostAverageFormatted(): String {
        val average = dollarCostAverage
        return NumberFormatter.formatCurrency(average, 2)
    }

    private fun makeDollarCostAverage(): BigDecimal {
        if (numberOwned.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO
        }
        val dollarCostAverage = purchasePriceTotal.divide(numberOwned, POHSettings.roundingMode)
        return dollarCostAverage.setScale(2, POHSettings.roundingMode)
    }
}
