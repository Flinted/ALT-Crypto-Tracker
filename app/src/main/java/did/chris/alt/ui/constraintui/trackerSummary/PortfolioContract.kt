package did.chris.alt.ui.constraintui.trackerSummary

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.Summary

/**
 * SummaryContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface PortfolioContractView : BaseContractView {
    fun updateForSummary(summary: Summary)
    fun setFABOnClickListener()
}

interface PortfolioContractPresenter : BaseContractPresenter<PortfolioContractView> {
    fun onDestroy()
}