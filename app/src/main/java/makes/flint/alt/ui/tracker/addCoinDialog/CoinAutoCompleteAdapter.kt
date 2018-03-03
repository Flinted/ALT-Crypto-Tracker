package makes.flint.alt.ui.tracker.addCoinDialog

import android.content.Context
import android.widget.ArrayAdapter
import makes.flint.alt.data.coinListItem.CoinListItem

/**
 * CoinAutoCompleteAdapter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinAutoCompleteAdapter private constructor(context: Context, layoutId: Int, items: Array<String>)
    : ArrayAdapter<String>(context, layoutId, items) {

    // Properties

    private lateinit var coinListItems: List<CoinListItem>

    // Internal Functions

    internal fun getCoinListItemForId(stringId: String): CoinListItem? {
        return coinListItems.find {
            it.searchKey == stringId
        }
    }

    // Companion

    companion object {
        fun makeInstanceFor(context: Context, layoutId: Int, items: List<CoinListItem>): CoinAutoCompleteAdapter {
            val stringArray = mapItemsToString(items)
            val instance = CoinAutoCompleteAdapter(context, layoutId, stringArray)
            instance.coinListItems = items
            return instance
        }

        private fun mapItemsToString(items: List<CoinListItem>): Array<String> {
            val keys = items.map {
                it.searchKey
            }
            return keys.toTypedArray()
        }
    }
}
