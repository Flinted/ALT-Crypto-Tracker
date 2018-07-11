package makes.flint.alt.ui.constraintui.trackerSummary

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.Summary

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