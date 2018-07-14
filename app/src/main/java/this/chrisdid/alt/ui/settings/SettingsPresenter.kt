package this.chrisdid.alt.ui.settings

import this.chrisdid.alt.base.BasePresenter
import this.chrisdid.alt.configuration.ALTSharedPreferences
import this.chrisdid.alt.data.dataController.DataController
import javax.inject.Inject

/**
 * SettingsPresenter
 */
class SettingsPresenter @Inject constructor(private val dataController: DataController) :
    BasePresenter<SettingsContractView>(), SettingsContractPresenter {

    override fun initialise() {
        view?.initialiseIconFields()
        view?.initialiseSortSpinner()
        view?.initialiseMarketSizeSelector()
        view?.initialiseIconPacksSelector()
        view?.initialiseHiddenValuesSwitch()
    }

    override fun newMarketLimitSet(adjustedLimit: Int) {
        ALTSharedPreferences.setMarketLimit(adjustedLimit)
        dataController.invalidateData()
    }

    override fun newSortPreferenceSet(preferenceKey: Int) {
        ALTSharedPreferences.setSort(preferenceKey)
    }

    override fun newHiddenValueStateSet(value: Boolean) {
        ALTSharedPreferences.setValuesHidden(value)
    }

    override fun newMarketThresholdSet(index: Int, value: Int) {
        ALTSharedPreferences.setValueForMarketThreshold(index, value.toFloat())
        ALTSharedPreferences.setCoinListRedrawRequired(true)
        dataController.invalidateData()
    }

    override fun newTrackerThresholdSet(index: Int, value: Int) {
        val decimalValue = value / 100f
        ALTSharedPreferences.setValueForTrackerThreshold(index, decimalValue)
        ALTSharedPreferences.setCoinListRedrawRequired(true)
        dataController.invalidateData()
    }

    override fun newIconPackSelected(iconKey: Int) {
        ALTSharedPreferences.setIconPack(iconKey)
        ALTSharedPreferences.setCoinListRedrawRequired(true)
        view?.initialiseIconFields()
        dataController.invalidateData()
    }
}