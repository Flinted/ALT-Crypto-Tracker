package makes.flint.alt.ui.constraintui.summaryChart

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
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.Summary
import makes.flint.alt.ui.constraintui.trackerSummary.SummaryContractPresenter
import makes.flint.alt.ui.constraintui.trackerSummary.SummaryContractView

/**
 * SummaryChartFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class SummaryChartFragment : BaseFragment(), SummaryContractView {

    // Properties

    private lateinit var pieChart: PieChart
    private lateinit var summaryPresenter: SummaryContractPresenter

    // Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chart, container, false)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        this.summaryPresenter = getPresenterComponent().provideSummaryPresenter()
        this.summaryPresenter.attachView(this)
        attachPresenter(summaryPresenter)
        bindViews(view)
        summaryPresenter.initialise()
        return view
    }

    override fun onResume() {
        super.onResume()
        pieChart.invalidate()
    }

    override fun onDestroy() {
        super.onDestroy()
        summaryPresenter.onDestroy()
    }

    // Overrides

    override fun setFABOnClickListener() {
    }

    override fun updateForSummary(summary: Summary) {
        val data = summary.getTrackerEntries()
        val pieEntries = data.map {
            PieEntry(it.currentValueUSD.toFloat(), it.symbol)
        }
        val context = requireContext()
        val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
        val colorPrimarySoft = ContextCompat.getColor(context, R.color.colorPrimarySoft)
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
        pieChart.setHoleColor(colorPrimarySoft)
        pieChart.transparentCircleRadius = 60f
        pieChart.legend.isEnabled = false
        pieChart.setDrawCenterText(true)
        pieChart.centerText = "Holdings %"
        pieChart.setCenterTextSize(12f)
        pieChart.setCenterTextColor(Color.WHITE)
        pieChart.isHighlightPerTapEnabled = true
        pieChart.description.isEnabled = false
        pieChart.animateXY(0, 1200)
        pieChart.data = pieData
    }

    // Private Functions

    private fun bindViews(view: View) {
        this.pieChart = view.findViewById(R.id.fragment_chart_pie)
    }
}
