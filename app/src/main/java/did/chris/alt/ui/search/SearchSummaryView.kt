package did.chris.alt.ui.search

import android.content.Context
import android.os.IBinder
import android.support.constraint.ConstraintLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import did.chris.alt.R
import did.chris.alt.data.response.marketSummary.MarketSummaryResponse

class SearchSummaryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    // Properties
    private lateinit var views: SearchSummaryViewHolder
    private var callback: SearchSummaryCallback? = null

    // Internal Functions
    internal fun initialise(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.search_bar_summary, this, true)
        views = SearchSummaryViewHolder(view)
        initialiseMarketSummaryOnClick()
        initialiseSearchButtonOnClick()
        initialiseSearchCancelOnClick()
        initialiseSearchInputOnClick()
        initialiseSearchTextChangedListener()
    }

    internal fun setCallback(callback: SearchSummaryCallback) {
        this.callback = callback
    }

    internal fun displayMarketSummary(
        context: Context,
        marketSummaryResponse: MarketSummaryResponse?
    ) {
        marketSummaryResponse ?: return
        views.marketTotalCap.text = context.getString(
            R.string.market_summary_marketcap, marketSummaryResponse.marketCapUSDFormatted()
        )
        views.marketCount.text = context.getString(
            R.string.market_summary_count,
            marketSummaryResponse.marketData.numberItems
        )
        views.marketVolume.text = context.getString(
            R.string.market_summary_volume, marketSummaryResponse.volume24HUSDFormatted()
        )
        views.marketChange1d.text = context.getString(
            R.string.market_summary_1d,
            marketSummaryResponse.marketData.twentyFourHourAverageFormatted()
        )
        views.marketChange1w.text = context.getString(
            R.string.market_summary_1w, marketSummaryResponse.marketData.sevenDayAverageFormatted()
        )
    }

    // Private Functions
    private fun initialiseSearchButtonOnClick() {
        views.coinSearchButton.setOnClickListener {
            callback?.searchStateRequested()
            views.marketSummary.visibility = View.INVISIBLE
            views.coinSearch.isEnabled = true
            views.coinSearch.visibility = View.VISIBLE
            views.coinSearchCancelButton.visibility = View.VISIBLE
            views.coinSearchButton.visibility = View.GONE
            views.coinSearch.requestFocus()
            callback?.keyboardRequested()
        }
    }

    private fun initialiseSearchCancelOnClick() {
        views.coinSearchCancelButton.setOnClickListener {
            callback?.cancelSearchRequested()
            views.coinSearch.text.clear()
            views.coinSearch.isEnabled = false
            views.marketSummary.visibility = View.VISIBLE
            views.coinSearch.visibility = View.INVISIBLE
            views.coinSearchCancelButton.visibility = View.GONE
            views.coinSearchButton.visibility = View.VISIBLE
            callback?.keyboardDismissed(views.coinSearch.windowToken)
        }
    }

    private fun initialiseSearchInputOnClick() {
        views.coinSearch.setOnClickListener {
            callback?.searchStateRequested()
            views.coinSearch.requestFocus()
        }
    }

    private fun initialiseSearchTextChangedListener() {
        views.coinSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                callback?.newSearchQuery(p0.toString())
            }
        })
    }

    private fun initialiseMarketSummaryOnClick() {
        views.marketSummary.setOnClickListener {
            callback?.searchStateRequested()
        }
    }
}

interface SearchSummaryCallback {

    // Functions
    fun keyboardRequested()
    fun keyboardDismissed(windowToken: IBinder)
    fun searchStateRequested()
    fun cancelSearchRequested()
    fun newSearchQuery(query: String)
}
