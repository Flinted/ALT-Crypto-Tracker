package makes.flint.alt.ui.settings

import android.support.v7.widget.SwitchCompat
import android.widget.ImageView
import android.widget.Spinner
import makes.flint.alt.R

/**
 * SettingsActivityViewHolder
 */
class SettingsActivityViewHolder(settingsActivity: SettingsActivity) {
    val sortSpinner: Spinner = settingsActivity.findViewById(R.id.settings_default_sort_selector)
    val marketSelector: MarketSizeNumberPicker =
        settingsActivity.findViewById(R.id.settings_market_size_selector)
    val hiddenValuesSwitch: SwitchCompat =
        settingsActivity.findViewById(R.id.settings_hide_values_switch)
    val upExtremeIcon: ImageView = settingsActivity.findViewById(R.id.settings_up_extreme_icon)
    val upSignificantIcon: ImageView = settingsActivity.findViewById(R.id.settings_up_significant_icon)
    val upModerateIcon: ImageView = settingsActivity.findViewById(R.id.settings_up_moderate_icon)
    val downExtremeIcon: ImageView = settingsActivity.findViewById(R.id.settings_down_extreme_icon)
    val downSignificantIcon: ImageView = settingsActivity.findViewById(R.id.settings_down_significant_icon)
    val downModerateIcon: ImageView = settingsActivity.findViewById(R.id.settings_down_moderate_icon)

}