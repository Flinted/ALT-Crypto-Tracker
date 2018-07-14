package this.chrisdid.alt.ui.constraintui.trackerSummary

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.Summary

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