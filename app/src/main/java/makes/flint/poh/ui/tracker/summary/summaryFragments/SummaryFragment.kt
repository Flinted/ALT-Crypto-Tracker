package makes.flint.poh.ui.tracker.summary.summaryFragments

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import makes.flint.poh.R
import makes.flint.poh.base.BaseFragment
import makes.flint.poh.configuration.IndicatorCustomiser
import makes.flint.poh.data.Summary
import makes.flint.poh.data.interfaces.assessChange

/**
 * SummaryFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SummaryFragment : BaseFragment(), SummaryContractView {

    private lateinit var initialValue: TextView
    private lateinit var currentValueUSD: TextView
    private lateinit var currentValueBTC: TextView
    private lateinit var changePercentage: TextView
    private lateinit var summaryPresenter: SummaryContractPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_summary, container, false)
        view ?: return super.onCreateView(inflater, container, savedInstanceState)
        this.summaryPresenter = getPresenterComponent().provideSummaryPresenter()
        summaryPresenter.attachView(this)
        attachPresenter(summaryPresenter)
        bindViews(view)
        summaryPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        summaryPresenter.onDestroy()
    }

    private fun bindViews(view: View) {
        this.initialValue = view.findViewById(R.id.summary_initial_value)
        this.currentValueUSD = view.findViewById(R.id.summary_current_value_USD)
        this.currentValueBTC = view.findViewById(R.id.summary_current_value_BTC)
        this.changePercentage = view.findViewById(R.id.summary_change_percentage)
    }

    override fun updateForSummary(summary: Summary) {
        initialValue.text = summary.initialValueFormatted()
        currentValueUSD.text = summary.currentValueFiatFormatted()
        currentValueBTC.text = summary.currentValueBTCFormatted()
        changePercentage.text = summary.percentageChangeFormatted()
        val customiser = IndicatorCustomiser()
        val status = summary.assessChange()
        val changeColor = customiser.getColor(status)
        changePercentage.setTextColor(ContextCompat.getColor(context, changeColor))
    }
}
