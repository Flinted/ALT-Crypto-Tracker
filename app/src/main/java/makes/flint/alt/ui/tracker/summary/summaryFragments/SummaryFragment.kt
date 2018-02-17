package makes.flint.alt.ui.tracker.summary.summaryFragments

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.configuration.IndicatorCustomiser
import makes.flint.alt.data.Summary
import makes.flint.alt.data.interfaces.assessChange

/**
 * SummaryFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SummaryFragment : BaseFragment(), SummaryContractView {

    private lateinit var initialValue: TextView
    private lateinit var currentValueUSD: TextView
    private lateinit var currentValueBTC: TextView
    private lateinit var changePercentage: TextView
    private lateinit var amountSpent: TextView
    private lateinit var amountSold: TextView
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
        this.amountSpent = view.findViewById(R.id.summary_amount_spent_value)
        this.amountSold = view.findViewById(R.id.summary_amount_sold_value)
    }

    override fun updateForSummary(summary: Summary) {
        initialValue.text = summary.initialValueFormatted()
        currentValueUSD.text = summary.currentValueFiatFormatted()
        currentValueBTC.text = summary.currentValueBTCFormatted()
        changePercentage.text = summary.percentageChangeFormatted()
        amountSpent.text = summary.amountSpentFormatted()
        amountSold.text = summary.amountSoldFormatted()
        val customizer = IndicatorCustomiser()
        val status = summary.assessChange()
        val changeColor = customizer.getColor(status)
        changePercentage.setTextColor(ContextCompat.getColor(context, changeColor))
    }
}
