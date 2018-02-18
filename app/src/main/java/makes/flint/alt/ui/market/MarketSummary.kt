package makes.flint.alt.ui.market

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.TextView
import makes.flint.alt.R
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse


/**
 * MarketSummary
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class MarketSummary : ConstraintLayout {

    private lateinit var marketSummaryTextView: TextView
    private lateinit var marketCapTextView: TextView
    private lateinit var vol24HTextView: TextView

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        this.marketSummaryTextView = this.findViewById(R.id.market_summary)
        this.marketCapTextView = this.findViewById(R.id.market_summary_market_cap)
        this.vol24HTextView = this.findViewById(R.id.market_summary_vol_24H)
    }

    internal fun displaySummary(marketSummary: MarketSummaryResponse) {
        val twentyFourHour = marketSummary.marketData.twentyFourHourAverageFormatted()
        val sevenDay = marketSummary.marketData.sevenDayAverageFormatted()
        val coins = marketSummary.marketData.numberItems
        val summaryString = context.getString(R.string.market_summary, twentyFourHour, sevenDay, coins)
        this.marketSummaryTextView.text = summaryString
    }
}