package makes.flint.poh.ui.tracker.trackerList

import makes.flint.poh.base.BaseContractPresenter
import makes.flint.poh.base.BaseContractView
import makes.flint.poh.data.Summary
import makes.flint.poh.data.trackerListItem.TrackerListItem
import rx.Observable

/**
 * TrackerContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface TrackerAdapterContractView : BaseContractView {
    var trackerEntries: MutableList<TrackerListItem>
    fun filterFor(input: String)
    fun initialiseTrackerList()
    fun showNoTrackerEntriesMessage()
    fun onTrackerEntrySelected(): Observable<TrackerListItem>
    fun onNoEntriesPresent(): Observable<Boolean>
    fun didHaveEntries()
    fun onRefreshStateChange(): Observable<Boolean>
    fun updateSummaryFragmentFor(summary: Summary)
    fun onSummaryPrepared(): Observable<Summary>
    fun onDestroy()
}

interface TrackerAdapterContractPresenter : BaseContractPresenter<TrackerAdapterContractView>