package did.chris.alt.ui.constraintui.coinDetail.coinDetailChart

import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import did.chris.alt.R

class CoinDetailChartViewHolder(view: View) {

    // Properties
    internal var chartHolder: FrameLayout = view.findViewById(R.id.coin_detail_chart_holder)
    internal var chartHighlightTimeStamp: TextView = view.findViewById(R.id.coin_detail_chart_highlight_timestamp)
    internal var chartHighlightValue: TextView = view.findViewById(R.id.coin_detail_chart_highlight_value)
    internal var progressBar: ProgressBar = view.findViewById(R.id.coin_detail_progress_bar)
    internal var chartSelectButtons: RadioGroup = view.findViewById(R.id.coin_detail_chart_select_buttons)
}
