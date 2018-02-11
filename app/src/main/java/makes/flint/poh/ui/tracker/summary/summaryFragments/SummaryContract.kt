package makes.flint.poh.ui.tracker.summary.summaryFragments

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.Summary

/**
 * SummaryContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface SummaryContractView : BaseContractView {
    fun updateForSummary(summary: Summary)
}

interface SummaryContractPresenter : BaseContractPresenter<SummaryContractView> {
    fun onDestroy()
}