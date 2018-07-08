package makes.flint.alt.ui.constraintui.trackerSummary

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.configuration.IndicatorCustomiser
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.Summary
import makes.flint.alt.data.interfaces.assessChange
import makes.flint.alt.layoutCoordination.tracker
import makes.flint.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import makes.flint.alt.ui.settings.SettingsActivity

/**
 * SummaryFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class SummaryFragment : BaseFragment(), SummaryContractView {

    // Properties

    private lateinit var views: SummaryFragmentViewHolder
    private lateinit var summaryPresenter: SummaryContractPresenter

    // Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)
        this.views = SummaryFragmentViewHolder(view)
        this.summaryPresenter = getPresenterComponent().provideSummaryPresenter()
        summaryPresenter.attachView(this)
        attachPresenter(summaryPresenter)
        summaryPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        summaryPresenter.onDestroy()
    }

    // Overrides

    override fun setFABOnClickListener() {
        views.summaryFAB.setOnClickListener {
            (activity as LayoutCoordinatable).updateLayout(tracker)
        }
        views.settingsFAB.setOnClickListener {
            SettingsActivity.start(activity as Activity)
        }
    }

    override fun updateForSummary(summary: Summary) {
        setAmountValues(summary)
        views.changePercentage.text = summary.percentageChangeFormatted()
        val customizer = IndicatorCustomiser(POHSettings.iconSet)
        val status = summary.assessChange()
        val changeColor = customizer.getColor(status)
        views.changePercentage.setTextColor(ContextCompat.getColor(requireContext(), changeColor))
    }

    private fun setAmountValues(summary: Summary) {
        if (POHSettings.hiddenValues) {
            setHiddenValues()
            return
        }
        setActualValues(summary)
    }

    private fun setHiddenValues() {
        val hiddenString = "HIDDEN"
        views.initialValue.text =
                getString(R.string.fragment_summary_initial_value, hiddenString)
        views.currentValueUSD.text =
                getString(R.string.fragment_summary_current_value_usd, hiddenString)
        views.currentValueBTC.text =
                getString(R.string.fragment_summary_current_value_btc, hiddenString)
        views.amountSpent.text =
                getString(R.string.fragment_summary_value_spent, hiddenString)
        views.amountSold.text =
                getString(R.string.fragment_summary_value_sold, hiddenString)
    }

    private fun setActualValues(summary: Summary) {
        views.initialValue.text =
                getString(R.string.fragment_summary_initial_value, summary.initialValueFormatted())
        views.currentValueUSD.text =
                getString(
                    R.string.fragment_summary_current_value_usd,
                    summary.currentValueFiatFormatted()
                )
        views.currentValueBTC.text =
                getString(
                    R.string.fragment_summary_current_value_btc,
                    summary.currentValueBTCFormatted()
                )
        views.amountSpent.text =
                getString(R.string.fragment_summary_value_spent, summary.amountSpentFormatted())
        views.amountSold.text =
                getString(R.string.fragment_summary_value_sold, summary.amountSoldFormatted())
    }
}
