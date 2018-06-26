package makes.flint.alt.factories

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import makes.flint.alt.R
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart.DetailChartMarker

/**
 * HistoricalDataChartFactory
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class HistoricalDataChartFactory(private var dataSet: Array<HistoricalDataUnitResponse>, private var label: String) {

    // Internal Functions

    internal fun updateDataSet(newData: Array<HistoricalDataUnitResponse>, label: String) {
        this.dataSet = newData
        this.label = label
    }

    internal fun createLineChart(context: Context): LineChart? {
        if (dataSet.isEmpty()) {
            return null
        }
        val entries = makeEntries()
        val lineDataSet = makeLineDataSet(entries, context)
        val chart = makeLineChart(context)
        chart.data = LineData(lineDataSet)
        chart.marker = DetailChartMarker(context, dataSet)
        return chart
    }

    // Private Functions

    private fun makeEntries(): MutableList<Entry> {
        val entries: MutableList<Entry> = mutableListOf()
        var count = 0f
        dataSet.forEach {
            val close = it?.close ?: return@forEach
            val entry = Entry(count, close)
            entries.add(entry)
            count++
        }
        return entries
    }

    private fun makeLineDataSet(entries: List<Entry>, context: Context): LineDataSet {
        return LineDataSet(entries, label).apply {
            setDrawIcons(false)
            setDrawValues(false)
            setDrawCircles(false)
            setDrawFilled(true)
            fillColor = ContextCompat.getColor(context, R.color.colorPrimary)
            color = ContextCompat.getColor(context, R.color.colorPrimary)
            fillAlpha = 255
            axisDependency = YAxis.AxisDependency.RIGHT
            highLightColor = Color.WHITE
            highlightLineWidth = 0.7f
        }
    }

    private fun makeLineChart(context: Context): LineChart {
        return LineChart(context).apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimarySoft))
            legend.isEnabled = false
            xAxis.isEnabled = false
            description.isEnabled = false
            setViewPortOffsets(0f, 0f, 0f, 0f)
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            setPinchZoom(false)
            animateXY(0, 1000)
        }
    }
}
