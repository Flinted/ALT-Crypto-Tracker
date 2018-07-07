package makes.flint.alt.ui.settings

import makes.flint.alt.base.BasePresenter
import makes.flint.alt.configuration.POHSettings
import makes.flint.alt.data.dataController.DataController
import javax.inject.Inject

/**
 * SettingsPresenter
 */
class SettingsPresenter @Inject constructor(private val dataController: DataController): BasePresenter<SettingsContractView>(), SettingsContractPresenter {

    override fun initialise() {
        view?.initialiseSortSpinner()
        view?.initialiseMarketSizeSelector()
    }

    override fun newMarketLimitSet(adjustedLimit: Int) {
        POHSettings.limit = adjustedLimit
        dataController
    }

    override fun newSortPreferenceSet(preferenceKey: Int) {
        POHSettings.sortPreference = preferenceKey
    }
}