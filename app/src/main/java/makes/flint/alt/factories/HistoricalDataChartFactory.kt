package makes.flint.alt.factories

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import makes.flint.alt.R
import makes.flint.alt.data.response.histoResponse.HistoricalDataUnitResponse

/**
 * HistoricalDataChartFactory
 * Copyright © 2018 Flint Makes. All rights reserved.
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
        return chart
    }

    internal fun createBarChart(context: Context): BarChart? {
        if (dataSet.isEmpty()) {
            return null
        }
        val entries = makeBarEntries()
        val barDataSet = makeBarDataSet(entries, context)
        val chart = makeBarChart(context)
        chart.data = BarData(barDataSet)
        return chart
    }

    internal fun createCandleChart(context: Context): CandleStickChart? {
        if (dataSet.isEmpty()) {
            return null
        }
        val entries = makeCandleEntries()
        val candleDataSet = makeCandleDataSet(entries, context)
        val chart = makeCandleChart(context)
        chart.data = CandleData(candleDataSet)
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

    private fun makeBarEntries(): MutableList<BarEntry> {
        val entries: MutableList<BarEntry> = mutableListOf()
        var count = 0f
        dataSet.forEach {
            val close = it?.close ?: return@forEach
            val entry = BarEntry(count, close)
            entries.add(entry)
            count++
        }
        return entries
    }

    private fun makeCandleEntries(): MutableList<CandleEntry> {
        val entries: MutableList<CandleEntry> = mutableListOf()
        var count = 0f
        dataSet.forEach {
            val close = it?.close ?: return@forEach
            val open = it.open ?: return@forEach
            val high = it.high ?: return@forEach
            val low = it.low ?: return@forEach
            val entry = CandleEntry(count, high, low, open, close)
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
            axisDependency = YAxis.AxisDependency.RIGHT
            highLightColor = Color.WHITE
            highlightLineWidth = 0.7f
            color = ContextCompat.getColor(context, R.color.colorAccent)
        }
    }

    private fun makeCandleDataSet(entries: MutableList<CandleEntry>, context: Context): CandleDataSet {
        return CandleDataSet(entries, label).apply {
            setDrawIcons(false)
            setDrawValues(false)
            shadowColorSameAsCandle = false
            axisDependency = YAxis.AxisDependency.RIGHT
            shadowColor = ContextCompat.getColor(context, R.color.colorPrimarySoft)
            shadowWidth = 1f
            highLightColor = Color.WHITE
            highlightLineWidth = 1f
            decreasingColor = ContextCompat.getColor(context, R.color.colorCandleRed)
            decreasingPaintStyle = Paint.Style.FILL_AND_STROKE
            increasingColor = ContextCompat.getColor(context, R.color.colorCandleGreen)
            increasingPaintStyle = Paint.Style.FILL_AND_STROKE
            neutralColor = Color.DKGRAY
        }
    }

    private fun makeLineChart(context: Context): LineChart {
        return LineChart(context).apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            xAxis.textColor = ContextCompat.getColor(context, R.color.colorOffWhite)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisLeft.isEnabled = false
            setPinchZoom(false)
            axisRight.textColor = ContextCompat.getColor(context, R.color.colorOffWhite)
            axisRight.setDrawGridLines(false)
            animateXY(1000, 1000)
        }
    }

    private fun makeBarChart(context: Context): BarChart {
        return BarChart(context).apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            xAxis.textColor = ContextCompat.getColor(context, R.color.colorOffWhite)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            setPinchZoom(false)
            axisLeft.isEnabled = false
            axisRight.textColor = ContextCompat.getColor(context, R.color.colorOffWhite)
            axisRight.setDrawGridLines(false)
            animateXY(1000, 1000)
        }
    }

    private fun makeCandleChart(context: Context): CandleStickChart {
        return CandleStickChart(context).apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            xAxis.textColor = ContextCompat.getColor(context, R.color.colorOffWhite)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisLeft.isEnabled = false
            setPinchZoom(false)
            axisRight.textColor = ContextCompat.getColor(context, R.color.colorOffWhite)
            axisRight.setDrawGridLines(false)
            animateXY(1000, 1000)
        }
    }

    private fun makeBarDataSet(entries: List<BarEntry>, context: Context): BarDataSet {
        return BarDataSet(entries, label).apply {
            color = ContextCompat.getColor(context, R.color.colorAccent)
            setDrawIcons(false)
            setDrawValues(false)
            axisDependency = YAxis.AxisDependency.LEFT
            highLightColor = Color.WHITE
        }
    }
}