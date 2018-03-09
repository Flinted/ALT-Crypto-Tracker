package makes.flint.alt.ui.main

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView

/**
 * MainContract
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
interface MainContractView: BaseContractView {
    fun initialiseBottomBar(startingTab: String)
    fun initialiseViewPager()
    fun initialiseData()
    fun initialiseSortingMaps()
}

interface MainContractPresenter: BaseContractPresenter<MainContractView> {
    fun emitData()
    fun storeSortToIdMap(map: HashMap<Int, Int>)
    fun storeIdToSortMap(map: HashMap<Int, Int>)
    fun getSortTypeForId(id: Int): Int?
    fun getIdForSortType(currentSort: Int): Int?
    fun onDestroy()
    fun updateSyncTime()
}