package did.chris.alt.ui.constraintui.trackerList.trackerListAdapter

import android.widget.Filter
import did.chris.alt.data.trackerListItem.TrackerListItem

class TrackerFilter(
    private var originalList: MutableList<TrackerListItem>,
    private var callback: TrackerFilterCallback
) : Filter() {

    // Overrides
    override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
        val filteredResults = getFilteredResults(constraint)
        val results = Filter.FilterResults()
        results.values = filteredResults
        return results
    }

    @Suppress("UNCHECKED_CAST")
    override fun publishResults(charSequence: CharSequence, results: Filter.FilterResults) {
        val filteredResults = (results.values as List<TrackerListItem>).toMutableList()
        callback.publishResults(filteredResults)
    }

    // Private Functions
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
}

interface TrackerFilterCallback {

    // Functions
    fun publishResults(filteredList: MutableList<TrackerListItem>)
}
