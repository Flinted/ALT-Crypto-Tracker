package did.chris.alt.ui.constraintui.trackerSummary

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.Summary

interface PortfolioContractView : BaseContractView {

    // Functions
    fun updateForSummary(summary: Summary)
    fun setFABOnClickListener()
}

interface PortfolioContractPresenter : BaseContractPresenter<PortfolioContractView> {

    // Functions
    fun onDestroy()
}