package makes.flint.alt.ui.settings

import android.widget.Spinner
import makes.flint.alt.R

/**
 * SettingsActivityViewHolder
 */
class SettingsActivityViewHolder(settingsActivity: SettingsActivity) {
    val sortSpinner: Spinner = settingsActivity.findViewById(R.id.settings_default_sort_selector)
    val marketSelector: MarketSizeNumberPicker = settingsActivity.findViewById(R.id.settings_market_size_selector)
}