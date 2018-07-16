package did.chris.alt.ui.settings

import android.support.v7.widget.SwitchCompat
import android.widget.ImageView
import android.widget.Spinner
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker
import did.chris.alt.R

class SettingsActivityViewHolder(settingsActivity: SettingsActivity) {

    // Properties
    internal val sortSpinner: Spinner = settingsActivity.findViewById(R.id.settings_default_sort_selector)
    internal val iconPackSpinner: Spinner = settingsActivity.findViewById(R.id.settings_icon_pack_selector)
    internal val marketSelector: Spinner =
        settingsActivity.findViewById(R.id.settings_market_size_selector)
    internal val hiddenValuesSwitch: SwitchCompat =
        settingsActivity.findViewById(R.id.settings_hide_values_switch)
    internal val upExtremeIcon: ImageView = settingsActivity.findViewById(R.id.settings_up_extreme_icon)
    internal val upSignificantIcon: ImageView =
        settingsActivity.findViewById(R.id.settings_up_significant_icon)
    internal val upModerateIcon: ImageView = settingsActivity.findViewById(R.id.settings_up_moderate_icon)
    internal val downExtremeIcon: ImageView = settingsActivity.findViewById(R.id.settings_down_extreme_icon)
    internal val downSignificantIcon: ImageView =
        settingsActivity.findViewById(R.id.settings_down_significant_icon)
    internal val downModerateIcon: ImageView =
        settingsActivity.findViewById(R.id.settings_down_moderate_icon)

    private val marketThresholdUpExtreme: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_up_extreme)
    private val marketThresholdUpSignificant: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_up_significant)
    private val marketThresholdUpModerate: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_up_moderate)
    private val marketThresholdDownModerate: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_down_moderate)
    private val marketThresholdDownSignificant: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_down_significant)
    private val marketThresholdDownExtreme: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_market_down_extreme)

    private val trackerThresholdUpExtreme: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_up_extreme)
    private val trackerThresholdUpSignificant: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_up_significant)
    private val trackerThresholdUpModerate: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_up_moderate)
    private val trackerThresholdDownModerate: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_down_moderate)
    private val trackerThresholdDownSignificant: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_down_significant)
    private val trackerThresholdDownExtreme: ScrollableNumberPicker =
        settingsActivity.findViewById(R.id.threshold_tracker_down_extreme)

    internal val marketThresholds = listOf(
        marketThresholdUpExtreme,
        marketThresholdUpSignificant,
        marketThresholdUpModerate,
        marketThresholdDownModerate,
        marketThresholdDownSignificant,
        marketThresholdDownExtreme
    )

    internal val trackerThresholds = listOf(
        trackerThresholdUpExtreme,
        trackerThresholdUpSignificant,
        trackerThresholdUpModerate,
        trackerThresholdDownModerate,
        trackerThresholdDownSignificant,
        trackerThresholdDownExtreme
    )
}
