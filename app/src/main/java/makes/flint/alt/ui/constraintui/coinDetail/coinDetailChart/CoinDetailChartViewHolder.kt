package makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import makes.flint.alt.R

/**
 * CoinDetailChartViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class CoinDetailChartViewHolder(view: View) {

    internal var chartHolder: FrameLayout = view.findViewById(R.id.coin_detail_chart_holder)
    internal var chartHighlight: TextView = view.findViewById(R.id.coin_detail_chart_highlight)
}