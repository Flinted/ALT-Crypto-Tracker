package makes.flint.alt.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.configuration.POHSettings

/**
 * SettingsActivity
 */
class SettingsActivity : BaseActivity(), SettingsContractView {

    private lateinit var views: SettingsActivityViewHolder
    private lateinit var presenter: SettingsContractPresenter

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, SettingsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_settings)
        views = SettingsActivityViewHolder(this)
        this.presenter = getPresenterComponent().provideSettingsPresenter()
        attachPresenter(presenter)
        presenter.attachView(this)
        presenter.initialise()
    }

    override fun initialiseSortSpinner() {
        views.sortSpinner.setSelection(POHSettings.sortPreference)
        views.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                preferenceKey: Int,
                p3: Long
            ) {
                presenter.newSortPreferenceSet(preferenceKey)
            }
        }
    }

    override fun initialiseMarketSizeSelector() {
        views.marketSelector.value = (POHSettings.limit / 100)
        views.marketSelector.setOnValueChangedListener { numberPicker, old, new ->
            val adjustedLimit = new * 100
            presenter.newMarketLimitSet(adjustedLimit)
        }
    }

    override fun initialiseHiddenValuesSwitch() {
        views.hiddenValuesSwitch.isChecked = POHSettings.hiddenValues
        views.hiddenValuesSwitch.setOnCheckedChangeListener { compoundButton, value ->
            presenter.newHiddenValueStateSet(value)
        }
    }

    override fun initialiseIconFields() {
        val icons = POHSettings.iconSet
        views.upExtremeIcon.setImageResource(icons.upExtreme)
        views.upSignificantIcon.setImageResource(icons.upSignificant)
        views.upModerateIcon.setImageResource(icons.upModerate)
        views.downExtremeIcon.setImageResource(icons.downExtreme)
        views.downSignificantIcon.setImageResource(icons.downSignificant)
        views.downModerateIcon.setImageResource(icons.downModerate)
    }
}
