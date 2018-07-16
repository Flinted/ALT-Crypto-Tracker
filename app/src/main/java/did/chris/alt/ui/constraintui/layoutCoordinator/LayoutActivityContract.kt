package did.chris.alt.ui.constraintui.layoutCoordinator

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView

interface LayoutActivityContractView : BaseContractView {

    // Functions
    fun loadInitialScreens()
    fun displayError(it: Throwable)
    fun displayRandomLoadingMessage()
}

interface LayoutActivityContractPresenter : BaseContractPresenter<LayoutActivityContractView> {

    // Functions
    fun emitData()
    fun onDestroy()
}