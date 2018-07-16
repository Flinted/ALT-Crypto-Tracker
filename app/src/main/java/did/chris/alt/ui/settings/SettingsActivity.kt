package did.chris.alt.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import did.chris.alt.R
import did.chris.alt.base.BaseActivity
import did.chris.alt.configuration.ALTSharedPreferences

class SettingsActivity : BaseActivity(), SettingsContractView {

    // Companion
    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, SettingsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    // Properties
    private lateinit var views: SettingsActivityViewHolder
    private lateinit var presenter: SettingsContractPresenter

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_settings)
        views = SettingsActivityViewHolder(this)
        this.presenter = getPresenterComponent().provideSettingsPresenter()
        attachPresenter(presenter)
        presenter.attachView(this)
        presenter.initialise()
    }

    // Overrides
    override fun initialiseSortSpinner() {
        views.sortSpinner.setSelection(ALTSharedPreferences.getSort())
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
        val options = resources.getStringArray(R.array.market_size_options)
        val currentSet = ALTSharedPreferences.getMarketLimit().toString()
        val index = options.indexOfFirst { it == currentSet }
        views.marketSelector.setSelection(index)
        views.marketSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                limitKey: Int,
                p3: Long
            ) {
                val selection = options[limitKey]
                presenter.newMarketLimitSet(selection.toInt())
            }
        }
    }

    override fun initialiseHiddenValuesSwitch() {
        views.hiddenValuesSwitch.isChecked = ALTSharedPreferences.getValuesHidden()
        views.hiddenValuesSwitch.setOnCheckedChangeListener { compoundButton, value ->
            presenter.newHiddenValueStateSet(value)
        }
        initialiseIconThresholds()
    }

    override fun initialiseIconFields() {
        val icons = ALTSharedPreferences.getIconPack()
        views.upExtremeIcon.setImageResource(icons.upExtreme)
        views.upSignificantIcon.setImageResource(icons.upSignificant)
        views.upModerateIcon.setImageResource(icons.upModerate)
        views.downExtremeIcon.setImageResource(icons.downExtreme)
        views.downSignificantIcon.setImageResource(icons.downSignificant)
        views.downModerateIcon.setImageResource(icons.downModerate)
    }


    override fun initialiseIconPacksSelector() {
        views.iconPackSpinner.setSelection(ALTSharedPreferences.getIconPackId())
        views.iconPackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                iconKey: Int,
                p3: Long
            ) {
                presenter.newIconPackSelected(iconKey)
            }
        }
    }

    // Private Functions
    private fun initialiseIconThresholds() {
        views.marketThresholds.forEachIndexed { index, numberPicker ->
            numberPicker.value = ALTSharedPreferences.getValueForMarketThreshold(index).toInt()
            numberPicker.setListener { value ->
                presenter.newMarketThresholdSet(index, value)
            }
        }
        views.trackerThresholds.forEachIndexed { index, numberPicker ->
            val percentageFloat = ALTSharedPreferences.getValueForTrackerThreshold(index) * 100
            numberPicker.value = percentageFloat.toInt()
            numberPicker.setListener { value ->
                presenter.newTrackerThresholdSet(index, value)
            }
        }
    }
}
