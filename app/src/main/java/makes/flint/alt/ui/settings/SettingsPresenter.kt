package makes.flint.alt.ui.settings

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.configuration.ALTSharedPreferences
import makes.flint.alt.data.dataController.DataController
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
}