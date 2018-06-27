package makes.flint.alt.factories.trackerBarChartFactory

import android.content.Context
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import makes.flint.alt.R
import makes.flint.alt.data.trackerListItem.TrackerListItem

/**
 * TrackerBarChartFactory
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
const val TRACKER_ITEM_LIMIT = 7f

class TrackerBarChartFactory {

    internal fun makeChart(context: Context, dataSet: List<TrackerListItem>): BarChart? {
        if (dataSet.isEmpty()) {
            return null
        }
        val barEntries = makeEntries(dataSet)
        val barDataSet = makeBarDataSet(barEntries.first, context)
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
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimarySoft))
            setDrawValueAboveBar(true)
            legend.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            xAxis.setDrawGridLines(false)
            xAxis.textColor = ContextCompat.getColor(context, R.color.colorAccent)
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

    private fun makeBarDataSet(entries: MutableList<BarEntry>, context: Context): BarDataSet {
        return BarDataSet(entries, "Tracker").apply {
            setDrawIcons(false)
            setDrawValues(true)
            valueTextSize = 10f
            color = ContextCompat.getColor(context, R.color.colorPrimary)
            axisDependency = YAxis.AxisDependency.RIGHT
            isHighlightEnabled = false
        }
    }
}
