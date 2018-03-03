package makes.flint.alt.data.trackerListItem

import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.coinListItem.CoinListItem
import makes.flint.alt.data.interfaces.TrackerAssessable
import makes.flint.alt.utility.NumberFormatter
import java.math.BigDecimal

/**
 * TrackerListItem
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerListItem(internal val name: String,
                      internal val symbol: String,
                      internal val id: String,
                      internal val associatedCoin: CoinListItem?,
                      internal var transactions: MutableList<TrackerTransaction>) : TrackerAssessable {

    // Properties

    internal val numberOwned = getNumberOwned()
    internal val numberOwnedFormatted = makeNumberOwnedFormatted()
    internal val symbolFormatted = makeSymbolFormatted()
    internal val purchasePriceTotal = makePurchasePriceTotalAccurate()
    internal val amountSpent = makeAmountSpent()
    internal val amountSold = makeAmountSold()
    internal val currentStanding = makeCurrentStanding()
    internal val currentValueUSD = makeCurrentValueUSDAccurate()
    internal val currentPriceUSDFormatted = makeCurrentValueUSDFormatted()
    internal val currentValueBTC = makeCurrentValueBTCAccurate()
    internal val currentPriceBTCFormatted = makeCurrentValueBTCFormatted()
    internal val dollarCostAverage = makeDollarCostAverage()
    internal val dollarCostAverageFormatted = makeDollarCostAverageFormatted()
    override var percentageChange = makePercentageChange()
    internal val percentageChangeFormatted = makePercentageChangeFormatted()

    // Private Functions

    private fun makeAmountSpent(): BigDecimal {
        return transactions.fold(BigDecimal.ZERO) { sum, entry ->
            val isSale = entry is TrackerTransactionSale
            val amountSpent = if (isSale) {
                BigDecimal.ZERO
            } else {
                entry.totalTransactionValue
            }
            sum.add(amountSpent)
        }
    }

    private fun makeAmountSold(): BigDecimal {
        return transactions.fold(BigDecimal.ZERO) { sum, entry ->
            val isSale = entry is TrackerTransactionSale
            val amountSpent = if (isSale) {
                entry.totalTransactionValue
            } else {
                BigDecimal.ZERO
            }
            sum.add(amountSpent)
        }
    }

    private fun makeCurrentStanding(): BigDecimal {
        return amountSpent.minus(amountSold)
    }


    private fun makeSymbolFormatted(): String {
        return "($symbol)"
    }

    private fun makeNumberOwnedFormatted(): String {
        return NumberFormatter.format(numberOwned, 8)
    }

    private fun getNumberOwned(): BigDecimal {
        return transactions.fold(BigDecimal.ZERO) { sum, entry ->
            sum.add(entry.quantity)
        }
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

    fun getCurrentAssetPrice(): BigDecimal? {
        return associatedCoin?.priceData?.priceUSD
    }

    fun getCurrentAssetPriceFormatted(): String {
        return associatedCoin?.priceData?.priceUSDFormatted ?: ""
    }

    private fun makeDollarCostAverageFormatted(): String {
        val average = dollarCostAverage
        return NumberFormatter.formatCurrency(average, 2)
    }

    private fun makeDollarCostAverage(): BigDecimal {
        if (numberOwned == BigDecimal.ZERO) {
            return BigDecimal.ZERO
        }
        val dollarCostAverage = purchasePriceTotal.divide(numberOwned, POHSettings.roundingMode)
        return dollarCostAverage.setScale(2, POHSettings.roundingMode)
    }
}
