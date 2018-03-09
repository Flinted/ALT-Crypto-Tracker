package makes.flint.alt.ui.tracker.trackerList

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.trackerListItem.TrackerListItem
import rx.Observable

/**
 * TrackerContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
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
    fun onDestroy()
}

interface TrackerAdapterContractPresenter : BaseContractPresenter<TrackerAdapterContractView>