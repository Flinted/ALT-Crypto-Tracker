package makes.flint.alt.ui.market.coinlist

import android.widget.Filter
import makes.flint.alt.data.coinListItem.CoinListItem

/**
 * CoinFilter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinFilter(private var originalList: MutableList<CoinListItem>, private var callback: CoinFilterCallback) : Filter() {

    override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
        val filteredResults = getFilteredResults(constraint)
        val results = Filter.FilterResults()
        results.values = filteredResults
        return results
    }

    private fun getFilteredResults(constraint: CharSequence): List<CoinListItem> {
        if (constraint.isEmpty()) {
            return originalList
        }
        val lowercaseConstraint = constraint.toString().toLowerCase()
        return originalList.filter {
            val nameMatch = it.name.toLowerCase().contains(lowercaseConstraint)
            val tickerMatch = it.symbol.toLowerCase().contains(lowercaseConstraint)
            return@filter nameMatch || tickerMatch
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun publishResults(charSequence: CharSequence, results: Filter.FilterResults) {
        val filteredResults = (results.values as List<CoinListItem>).toMutableList()
        callback.publishResults(filteredResults)
    }
}

interface CoinFilterCallback {
    fun publishResults(filteredList: MutableList<CoinListItem>)
}