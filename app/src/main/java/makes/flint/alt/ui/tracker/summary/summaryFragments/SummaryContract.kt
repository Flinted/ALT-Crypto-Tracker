package makes.flint.alt.ui.tracker.summary.summaryFragments

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.Summary

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