package makes.flint.poh.ui.tracker.summary

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import makes.flint.poh.R
import makes.flint.poh.base.BaseFragment
import makes.flint.poh.data.Summary
import makes.flint.poh.ui.interfaces.SummaryUpdatable

/**
 * SummaryChartFragment
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class SummaryChartFragment : BaseFragment(), SummaryUpdatable {

    private lateinit var pieChart: PieChart

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_chart, container, false)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        bindViews(view)
        return view
    }

    private fun bindViews(view: View) {
        this.pieChart = view.findViewById(R.id.fragment_chart_pie)
    }

    override fun updateForSummary(summary: Summary) {
        val data = summary.getTrackerEntries()
        val pieEntries = data.map {
            PieEntry(it.currentValueUSD.toFloat(), it.symbol)
        }
        val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
        val colorPrimaryDark = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        val colorAccent = ContextCompat.getColor(context, R.color.colorAccent)
        val chartColor2 = ContextCompat.getColor(context, R.color.chartSecondary)
        val chartColor3 = ContextCompat.getColor(context, R.color.chartTertiary)
        val colorBlack = ContextCompat.getColor(context, R.color.colorOffBlack)
        val dataSet = PieDataSet(pieEntries, "Holding")
        val pieData = PieData(dataSet)
        dataSet.setColors(colorAccent, chartColor2, chartColor3)
        dataSet.valueTextColor = colorBlack
        dataSet.valueTextSize = 15f
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelColor(colorBlack)
        pieChart.description.isEnabled = false
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.setTransparentCircleColor(colorPrimary)
        pieChart.holeRadius = 50f
        pieChart.setHoleColor(colorPrimaryDark)
        pieChart.transparentCircleRadius = 60f
        pieChart.legend.isEnabled = false
        pieChart.setDrawCenterText(true)
        pieChart.centerText = "Holdings %"
        pieChart.setCenterTextSize(18f)
        pieChart.setCenterTextColor(Color.WHITE)
        pieChart.isHighlightPerTapEnabled = true
        pieChart.description.isEnabled = false
        pieChart.animateXY(1500, 1500)
        pieChart.data = pieData
    }

    override fun onResume() {
        super.onResume()
        pieChart.invalidate()
    }
}
