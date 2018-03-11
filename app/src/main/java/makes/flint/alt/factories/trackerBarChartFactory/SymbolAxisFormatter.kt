package makes.flint.alt.factories.trackerBarChartFactory

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

/**
 * SymbolAxisFormatter
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class SymbolAxisFormatter(private val labels: List<String>) : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return try {
            labels[value.toInt()]
        } catch (e: Exception) {
            ""
        }
    }
}
