package this.chrisdid.alt.ui.constraintui.trackerList.trackerListAdapter

import this.chrisdid.alt.base.BaseContractPresenter
import this.chrisdid.alt.base.BaseContractView
import this.chrisdid.alt.data.trackerListItem.TrackerListItem
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
    fun onNoEntriesPresent(): Pair<Observable<Boolean>, Boolean>
    fun didHaveEntries()
    fun onRefreshStateChange(): Observable<Boolean>
    fun onDestroy()
}

interface TrackerAdapterContractPresenter : BaseContractPresenter<TrackerAdapterContractView>