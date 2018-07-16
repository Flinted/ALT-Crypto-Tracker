package did.chris.alt.ui.constraintui.portfoliopiechart

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
import did.chris.alt.R
import did.chris.alt.base.BaseFragment
import did.chris.alt.data.Summary
import did.chris.alt.ui.constraintui.trackerSummary.PortfolioContractPresenter
import did.chris.alt.ui.constraintui.trackerSummary.PortfolioContractView

class PortfolioPieChartFragment : BaseFragment(), PortfolioContractView {

    // Properties
    private lateinit var pieChart: PieChart
    private lateinit var portfolioPieChartPresenter: PortfolioContractPresenter

    // Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tracker_pie_chart, container, false)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        this.portfolioPieChartPresenter = getPresenterComponent().provideSummaryPresenter()
        this.portfolioPieChartPresenter.attachView(this)
        attachPresenter(portfolioPieChartPresenter)
        bindViews(view)
        portfolioPieChartPresenter.initialise()
        return view
    }

    override fun onResume() {
        super.onResume()
        pieChart.invalidate()
    }

    override fun onDestroy() {
        super.onDestroy()
        portfolioPieChartPresenter.onDestroy()
    }

    // Overrides
    override fun updateForSummary(summary: Summary) {
        val data = summary.getTrackerEntries()
        val pieEntries = data.map {
            PieEntry(it.currentValueUSD.toFloat(), it.symbol)
        }
        val context = requireContext()
        val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
        val colorAccent = ContextCompat.getColor(context, R.color.colorAccent)
        val chartColor2 = ContextCompat.getColor(context, R.color.chartSecondary)
        val chartColor3 = ContextCompat.getColor(context, R.color.chartTertiary)
        val chartColor4 = ContextCompat.getColor(context, R.color.chartQuaternary)
        val chartColor5 = ContextCompat.getColor(context, R.color.chartQuinary)
        val colorBlack = ContextCompat.getColor(context, R.color.colorOffBlack)
        val dataSet = PieDataSet(pieEntries, "Holding")
        val pieData = PieData(dataSet)
        dataSet.setColors(colorAccent, chartColor2, chartColor3, chartColor4, chartColor5)
        dataSet.valueTextColor = colorBlack
        dataSet.valueTextSize = 15f
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelColor(colorBlack)
        pieChart.description.isEnabled = false
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.setTransparentCircleColor(colorPrimary)
        pieChart.holeRadius = 50f
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.transparentCircleRadius = 60f
        pieChart.legend.isEnabled = false
        pieChart.setDrawCenterText(true)
        pieChart.centerText = "Holdings %"
        pieChart.setCenterTextSize(12f)
        pieChart.setCenterTextColor(Color.WHITE)
        pieChart.isHighlightPerTapEnabled = true
        pieChart.description.isEnabled = false
        pieChart.animateXY(0, 1000)
        pieChart.data = pieData
    }

    override fun setFABOnClickListener() {
    }

    // Private Functions
    private fun bindViews(view: View) {
        this.pieChart = view.findViewById(R.id.fragment_chart_pie)
    }
}
