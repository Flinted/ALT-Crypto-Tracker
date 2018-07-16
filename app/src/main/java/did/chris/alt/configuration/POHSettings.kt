package did.chris.alt.configuration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import did.chris.alt.ui.interfaces.SORT_RANK
import java.math.RoundingMode
import java.util.*

// Const Properties
private const val SHARED_PREFERENCES_KEY = "ALT_SETTINGS"
private const val CURRENCY = "currency"
private const val HIDDEN_VALUES = "hiddenValues"
private const val FIRST_LOAD = "firstLoad"
private const val REFRESH_GAP = "refreshGap"
private const val EXCHANGE_SOURCE = "exchangeSource"
private const val MARKET_LIMIT = "marketLimit"
private const val SORT_BY = "sortBy"
private const val ICON_PACK = "iconPack"
private const val COIN_REDRAW_REQUIRED = "coinRedrawRequired"
private const val TRACKER_REDRAW_REQUIRED = "trackerRedrawRequired"
private const val MARKET_THRESHOLDS = "marketThresholds"
private const val TRACKER_THRESHOLDS = "trackerThresholds"

object ALTSharedPreferences {

    // Properties
    private lateinit var preferences: SharedPreferences
    private val marketDefaults = listOf(50f, 15f, 5f, -5f, -15f, -50f)
    private val trackerDefaults = listOf(1.5f, 0.5f, 0.15f, -0.1f, -0.25f, -0.5f)

    // Internal Functions
    internal fun initialise(context: Context) {
        preferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Activity.MODE_PRIVATE)
    }

    internal fun setSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    internal fun removeSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    internal fun getCurrencyCode(): String = preferences.getString(CURRENCY, "USD")
    internal fun getCurrency(): Currency = Currency.getInstance(getCurrencyCode())
    internal fun getSymbol(): String {
        val currency = getCurrency()
        return currency.currencyCode
    }

    internal fun getRoundingMode() = RoundingMode.HALF_EVEN

    internal fun getValuesHidden() = preferences.getBoolean(HIDDEN_VALUES, false)

    internal fun getFirstLoad() = preferences.getBoolean(FIRST_LOAD, true)

    internal fun getRefreshGap() = preferences.getLong(REFRESH_GAP, 5L)

    internal fun getExchange(): String = preferences.getString(EXCHANGE_SOURCE, "CCCAGG")

    internal fun getMarketLimit() = preferences.getInt(MARKET_LIMIT, 1000)

    internal fun getSort() = preferences.getInt(SORT_BY, SORT_RANK)

    internal fun getIconPackId() = preferences.getInt(ICON_PACK, 0)

    internal fun getIconPack(): IconPack {
        val packKey = getIconPackId()
        return when (packKey) {
            0    -> DefaultIconPack()
            1    -> ArrowIconPack()
            2    -> SignalIconPack()
            3    -> DiceIconPack()
            else -> DefaultIconPack()
        }
    }

    internal fun getCoinListRedrawRequired() = preferences.getBoolean(COIN_REDRAW_REQUIRED, false)

    internal fun getTrackerListRedrawRequired() =
        preferences.getBoolean(TRACKER_REDRAW_REQUIRED, false)

    internal fun setTrackerListRedrawRequired(required: Boolean) =
        putBoolean(TRACKER_REDRAW_REQUIRED, required)

    internal fun setCoinListRedrawRequired(required: Boolean) =
        putBoolean(COIN_REDRAW_REQUIRED, required)

    internal fun setSort(sort: Int) = putInt(SORT_BY, sort)

    internal fun setMarketLimit(limit: Int) = putInt(MARKET_LIMIT, limit)

    internal fun setValuesHidden(hidden: Boolean) = putBoolean(HIDDEN_VALUES, hidden)

    internal fun setIconPack(key: Int) = putInt(ICON_PACK, key)

    internal fun getValueForMarketThreshold(index: Int): Float {
        val key = MARKET_THRESHOLDS + index
        val default = marketDefaults[index]
        return preferences.getFloat(key, default)
    }

    internal fun getValueForTrackerThreshold(index: Int): Float {
        val key = TRACKER_THRESHOLDS + index
        val default = trackerDefaults[index]
        return preferences.getFloat(key, default)
    }

    @SuppressLint("ApplySharedPref")
    internal fun setValueForMarketThreshold(index: Int, value: Float) {
        val key = MARKET_THRESHOLDS + index
        preferences.edit().putFloat(key, value).commit()
    }

    @SuppressLint("ApplySharedPref")
    internal fun setValueForTrackerThreshold(index: Int, value: Float) {
        val key = TRACKER_THRESHOLDS + index
        preferences.edit().putFloat(key, value).commit()
    }

    // Private Functions
    @SuppressLint("ApplySharedPref")
    private fun putInt(key: String, value: Int) {
        preferences
            .edit()
            .putInt(key, value)
            .commit()
    }

    @SuppressLint("ApplySharedPref")
    private fun putString(key: String, value: String) {
        preferences
            .edit()
            .putString(key, value)
            .commit()
    }

    @SuppressLint("ApplySharedPref")
    private fun putBoolean(key: String, value: Boolean) {
        preferences
            .edit()
            .putBoolean(key, value)
            .commit()
    }
}
