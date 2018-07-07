package makes.flint.alt.ui.settings

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView

/**
 * SettingsContract
 */
interface SettingsContractView : BaseContractView {
    fun initialiseSortSpinner()
    fun initialiseMarketSizeSelector()
}

interface SettingsContractPresenter : BaseContractPresenter<SettingsContractView> {
    abstract fun newMarketLimitSet(adjustedLimit: Int)
    abstract fun newSortPreferenceSet(preferenceKey: Int)

}