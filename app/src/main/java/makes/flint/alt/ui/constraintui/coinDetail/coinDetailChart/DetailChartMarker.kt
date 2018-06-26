package makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import makes.flint.alt.R
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse
import makes.flint.alt.utility.NumberFormatter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import java.math.BigDecimal

/**
 * DetailChartMarker
 */
class DetailChartMarker(context: Context, private val dataSet: Array<HistoricalDataUnitResponse>) : MarkerView(context, R.layout.marker_coin_detail) {

    private val title: TextView = findViewById(R.id.marker_date)
    private val value: TextView = findViewById(R.id.marker_value)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val xValueDataPoint = dataSet[e!!.x.toInt()]
        title.text = Instant.ofEpochSecond(xValueDataPoint.time!!.toLong()).atZone(ZoneId.systemDefault()).toLocalDate().toString()

        val close = BigDecimal(xValueDataPoint.close!!.toDouble())
        value.text = NumberFormatter.formatCurrency(close)

        super.refreshContent(e, highlight)
    }
}