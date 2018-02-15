package makes.flint.alt.ui.tracker.trackerList

import android.widget.Filter
import makes.flint.alt.data.trackerListItem.TrackerListItem

/**
 * TrackerFilter
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
class TrackerFilter(private var originalList: MutableList<TrackerListItem>, private var callback: TrackerFilterCallback) : Filter
() {

    override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
        val filteredResults = getFilteredResults(constraint)
        val results = Filter.FilterResults()
        results.values = filteredResults
        return results
    }

    private fun getFilteredResults(constraint: CharSequence): List<TrackerListItem> {
        if (constraint.isEmpty()) {
            return originalList
        }
        val lowercaseConstraint = constraint.toString().toLowerCase()
        return originalList.filter {
            val nameMatch = it.name.toLowerCase().contains(lowercaseConstraint)
            val symbolMatch = it.symbol.toLowerCase().contains(lowercaseConstraint)
            return@filter nameMatch || symbolMatch
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun publishResults(charSequence: CharSequence, results: Filter.FilterResults) {
        val filteredResults = (results.values as List<TrackerListItem>).toMutableList()
        callback.publishResults(filteredResults)
    }
}

interface TrackerFilterCallback {
    fun publishResults(filteredList: MutableList<TrackerListItem>)
}
