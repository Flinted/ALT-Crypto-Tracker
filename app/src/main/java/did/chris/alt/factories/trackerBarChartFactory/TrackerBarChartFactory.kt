package did.chris.alt.factories.trackerBarChartFactory

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import did.chris.alt.R
import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.data.trackerListItem.TrackerListItem


/**
 * TrackerBarChartFactory
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
const val TRACKER_ITEM_LIMIT = 6f

class TrackerBarChartFactory {

    internal fun makeChart(context: Context, dataSet: List<TrackerListItem>): BarChart? {
        if (dataSet.isEmpty()) {
            return null
        }
        val barEntries = makeEntries(dataSet)
        val barDataSet = makeBarDataSet(context, barEntries.first)
        val labelFormatter = SymbolAxisFormatter(barEntries.second)
        val chart = makeBarChart(context, labelFormatter)
        val barData = BarData(barDataSet)
        chart.data = barData
        chart.invalidate()
        chart.setVisibleXRangeMaximum(TRACKER_ITEM_LIMIT)
        return chart
    }

    private fun makeBarChart(context: Context, labelFormatter: IAxisValueFormatter): BarChart {
        return BarChart(context).apply {
            setBackgroundColor(Color.TRANSPARENT)
            setDrawValueAboveBar(true)
            legend.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            xAxis.setDrawGridLines(false)
            xAxis.textColor = ContextCompat.getColor(context, R.color.colorOffWhite)
            xAxis.valueFormatter = labelFormatter
            xAxis.granularity = 1f
            description.isEnabled = false
            setViewPortOffsets(0f, 0f, 0f, 0f)
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            setPinchZoom(false)
            animateXY(0, 500)
        }
    }

    private fun makeEntries(dataSet: List<TrackerListItem>): Pair<MutableList<BarEntry>, MutableList<String>> {
        val entries: MutableList<BarEntry> = mutableListOf()
        val xAxisLabels: MutableList<String> = mutableListOf()
        var count = 0f
        dataSet.forEach {
            val value = it.currentValueUSD.toFloat()
            val entry = BarEntry(count, value)
            entries.add(entry)
            xAxisLabels.add(it.symbol)
            count++
        }
        return Pair(entries, xAxisLabels)
    }

    private fun makeBarDataSet(
        context: Context,
        entries: MutableList<BarEntry>
    ): BarDataSet {
        return BarDataSet(entries, "Tracker").apply {
            setDrawIcons(false)
            setDrawValues(!ALTSharedPreferences.getValuesHidden())
            valueTextSize = 10f
            valueTextColor = ContextCompat.getColor(context, R.color.colorOffWhite)
            barBorderColor = ContextCompat.getColor(context, R.color.colorAccent)
            barBorderWidth = 2f
            color = Color.TRANSPARENT
            axisDependency = YAxis.AxisDependency.RIGHT
            isHighlightEnabled = false
        }
    }
}
