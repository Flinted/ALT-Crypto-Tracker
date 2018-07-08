package makes.flint.alt.ui.settings

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.configuration.POHSettings
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
        POHSettings.limit = adjustedLimit
        dataController.invalidateData()
    }

    override fun newSortPreferenceSet(preferenceKey: Int) {
        POHSettings.sortPreference = preferenceKey
    }

    override fun newHiddenValueStateSet(value: Boolean) {
        POHSettings.hiddenValues = value
    }
}