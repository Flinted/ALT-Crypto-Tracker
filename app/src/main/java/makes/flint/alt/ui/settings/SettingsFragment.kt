package makes.flint.alt.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.configuration.SettingsData

/**
 * SettingsFragment
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */

// Settings to be completely removed/reworked into navigation drawer.  This is kept for reference only.
class SettingsFragment : BaseFragment(), SettingsContractView {

    private lateinit var startScreenSpinner: Spinner
    private lateinit var currencySpinner: Spinner
    private lateinit var defaultSortSpinner: Spinner
    private lateinit var marketSizeSpinner: Spinner

    private lateinit var marketUp1: EditText
    private lateinit var marketUp2: EditText
    private lateinit var marketUp3: EditText
    private lateinit var marketDown1: EditText
    private lateinit var marketDown2: EditText
    private lateinit var marketDown3: EditText

    private lateinit var trackerUp1: EditText
    private lateinit var trackerUp2: EditText
    private lateinit var trackerUp3: EditText
    private lateinit var trackerDown1: EditText
    private lateinit var trackerDown2: EditText
    private lateinit var trackerDown3: EditText

    private lateinit var settingsPresenter: SettingsContractPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_settings, container, false)
        this.settingsPresenter = getPresenterComponent().provideSettingsPresenter()
        this.settingsPresenter.attachView(this)
        attachPresenter(settingsPresenter)
        bindViews(view)
        settingsPresenter.initialise()
        return view
    }

    private fun bindViews(view: View?) {
        view ?: return
        this.marketSizeSpinner = view.findViewById(R.id.settings_market_size_selector)
        this.currencySpinner = view.findViewById(R.id.settings_currency_selector)
        this.startScreenSpinner = view.findViewById(R.id.settings_start_screen_selector)
        this.defaultSortSpinner = view.findViewById(R.id.settings_default_sort_selector)
        this.marketUp1 = view.findViewById(R.id.settings_market_up_1_input)
        this.marketUp2 = view.findViewById(R.id.settings_market_up_2_input)
        this.marketUp3 = view.findViewById(R.id.settings_market_up_3_input)
        this.marketDown3 = view.findViewById(R.id.settings_market_down_3_input)
        this.marketDown2 = view.findViewById(R.id.settings_market_down_2_input)
        this.marketDown1 = view.findViewById(R.id.settings_market_down_1_input)
        this.trackerUp1 = view.findViewById(R.id.settings_tracker_up_1_input)
        this.trackerUp2 = view.findViewById(R.id.settings_tracker_up_2_input)
        this.trackerUp3 = view.findViewById(R.id.settings_tracker_up_3_input)
        this.trackerDown3 = view.findViewById(R.id.settings_tracker_down_3_input)
        this.trackerDown2 = view.findViewById(R.id.settings_tracker_down_2_input)
        this.trackerDown1 = view.findViewById(R.id.settings_tracker_down_1_input)
    }

    override fun displayCurrentSettings(settings: SettingsData) {
        val editTextBuffer = TextView.BufferType.EDITABLE
        val spinnerLayout = android.R.layout.simple_spinner_item
        marketSizeSpinner.adapter = ArrayAdapter.createFromResource(context, R.array.market_size_options, spinnerLayout)
        startScreenSpinner.adapter = ArrayAdapter.createFromResource(context, R.array.start_screen_options, spinnerLayout)
        currencySpinner.adapter = ArrayAdapter.createFromResource(context, R.array.currency_options, spinnerLayout)
        marketUp1.setText(settings.marketUp1.toString(), editTextBuffer)
        marketUp2.setText(settings.marketUp2.toString(), editTextBuffer)
        marketUp3.setText(settings.marketUp3.toString(), editTextBuffer)
        marketDown1.setText(settings.marketDown1.toString(), editTextBuffer)
        marketDown2.setText(settings.marketDown2.toString(), editTextBuffer)
        marketDown3.setText(settings.marketDown3.toString(), editTextBuffer)
        trackerUp1.setText(settings.trackerUp1.toString(), editTextBuffer)
        trackerUp2.setText(settings.trackerUp2.toString(), editTextBuffer)
        trackerUp3.setText(settings.trackerUp3.toString(), editTextBuffer)
        trackerDown1.setText(settings.trackerDown1.toString(), editTextBuffer)
        trackerDown2.setText(settings.trackerDown2.toString(), editTextBuffer)
        trackerDown3.setText(settings.trackerDown3.toString(), editTextBuffer)
    }
}