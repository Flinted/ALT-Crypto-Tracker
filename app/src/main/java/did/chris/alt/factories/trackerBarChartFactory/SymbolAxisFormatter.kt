package did.chris.alt.factories.trackerBarChartFactory

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class SymbolAxisFormatter(private val labels: List<String>) : IAxisValueFormatter {

    // Overrides
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return try {
            labels[value.toInt()]
        } catch (e: Exception) {
            ""
        }
    }
}
