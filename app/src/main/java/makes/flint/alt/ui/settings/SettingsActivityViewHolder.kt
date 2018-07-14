package makes.flint.alt.ui.settings

import android.support.v7.widget.SwitchCompat
import android.widget.ImageView
import android.widget.Spinner
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker
import makes.flint.alt.R

/**
 * SettingsActivityViewHolder
 */
class SettingsActivityViewHolder(settingsActivity: SettingsActivity) {
    val sortSpinner: Spinner = settingsActivity.findViewById(R.id.settings_default_sort_selector)
    val iconPackSpinner: Spinner = settingsActivity.findViewById(R.id.settings_icon_pack_selector)
    val marketSelector: Spinner =
        settingsActivity.findViewById(R.id.settings_market_size_selector)
    val hiddenValuesSwitch: SwitchCompat =
        settingsActivity.findViewById(R.id.settings_hide_values_switch)
    val upExtremeIcon: ImageView = settingsActivity.findViewById(R.id.settings_up_extreme_icon)
    val upSignificantIcon: ImageView =
        settingsActivity.findViewById(R.id.settings_up_significant_icon)
    val upModerateIcon: ImageView = settingsActivity.findViewById(R.id.settings_up_moderate_icon)
    val downExtremeIcon: ImageView = settingsActivity.findViewById(R.id.settings_down_extreme_icon)
    val downSignificantIcon: ImageView =
        settingsActivity.findViewById(R.id.settings_down_significant_icon)
    val downModerateIcon: ImageView =
        settingsActivity.findViewById(R.id.settings_down_moderate_icon)

    val marketThresholdUpExtreme: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_up_extreme)
    val marketThresholdUpSignificant: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_up_significant)
    val marketThresholdUpModerate: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_up_moderate)
    val marketThresholdDownModerate: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_down_moderate)
    val marketThresholdDownSignificant: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_down_significant)
    val marketThresholdDownExtreme: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_down_extreme)

    val trackerThresholdUpExtreme: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_up_extreme)
    val trackerThresholdUpSignificant: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_up_significant)
    val trackerThresholdUpModerate: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_up_moderate)
    val trackerThresholdDownModerate: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_down_moderate)
    val trackerThresholdDownSignificant: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_down_significant)
    val trackerThresholdDownExtreme: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_down_extreme)

    val marketThresholds = listOf(
        marketThresholdUpExtreme,
        marketThresholdUpSignificant,
        marketThresholdUpModerate,
        marketThresholdDownModerate,
        marketThresholdDownSignificant,
        marketThresholdDownExtreme
    )

    val trackerThresholds = listOf(
        trackerThresholdUpExtreme,
        trackerThresholdUpSignificant,
        trackerThresholdUpModerate,
        trackerThresholdDownModerate,
        trackerThresholdDownSignificant,
        trackerThresholdDownExtreme
    )


}