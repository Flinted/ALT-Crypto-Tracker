package makes.flint.alt.ui.settings

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView

/**
 * SettingsContract
 */
interface SettingsContractView : BaseContractView {
    fun initialiseSortSpinner()
    fun initialiseMarketSizeSelector()
    fun initialiseHiddenValuesSwitch()
    fun initialiseIconFields()
    fun initialiseIconPacksSelector()
}

interface SettingsContractPresenter : BaseContractPresenter<SettingsContractView> {
    fun newMarketLimitSet(adjustedLimit: Int)
    fun newSortPreferenceSet(preferenceKey: Int)
    fun newHiddenValueStateSet(value: Boolean)
    fun newIconPackSelected(iconKey: Int)
    fun newMarketThresholdSet(index: Int, value: Int)
    fun newTrackerThresholdSet(index: Int, value: Int)
}