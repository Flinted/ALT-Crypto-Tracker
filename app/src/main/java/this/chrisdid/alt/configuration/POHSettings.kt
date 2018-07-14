package this.chrisdid.alt.configuration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat
import this.chrisdid.alt.R
import this.chrisdid.alt.ui.interfaces.SORT_RANK
import this.chrisdid.doppel.backgroundproviders.DoppelViewTypeColorProvider
import this.chrisdid.doppel.doppelbuilder.DoppelConfigurationBuilder
import this.chrisdid.doppel.doppelbuilder.configuration.DoppelConfiguration
import java.math.RoundingMode
import java.util.*

/**
 * POHSettings
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
const val START_MARKET = "StartMarket"
const val START_TRACKER = "StartTracker"
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

    private lateinit var preferences: SharedPreferences

    fun initialise(context: Context) {
        preferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Activity.MODE_PRIVATE)
    }

    fun setSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun removeSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun getCurrencyCode(): String = preferences.getString(CURRENCY, "USD")
    fun getCurrency(): Currency = Currency.getInstance(getCurrencyCode())
    fun getSymbol(): String {
        val currency = getCurrency()
        return currency.currencyCode
    }

    fun getRoundingMode() = RoundingMode.HALF_EVEN
    fun getValuesHidden() = preferences.getBoolean(HIDDEN_VALUES, false)
    fun getFirstLoad() = preferences.getBoolean(FIRST_LOAD, true)
    fun getRefreshGap() = preferences.getLong(REFRESH_GAP, 5L)
    fun getExchange(): String = preferences.getString(EXCHANGE_SOURCE, "CCCAGG")
    fun getMarketLimit() = preferences.getInt(MARKET_LIMIT, 1000)
    fun getSort() = preferences.getInt(SORT_BY, SORT_RANK)
    fun getIconPackId() = preferences.getInt(ICON_PACK, 0)
    fun getIconPack(): IconPack {
        val packKey = getIconPackId()
        return when (packKey) {
            0    -> DefaultIconPack()
            1    -> ArrowIconPack()
            2    -> SignalIconPack()
            3    -> DiceIconPack()
            else -> DefaultIconPack()
        }
    }

    private var doppelConfiguration: DoppelConfiguration? = null

    fun getDoppelConfiguration(context: Context): DoppelConfiguration {
        val configuration = doppelConfiguration
        configuration?.let { return configuration }
        doppelConfiguration = makeDoppelConfiguration(context)
        return doppelConfiguration!!
    }

    private fun makeDoppelConfiguration(context: Context): DoppelConfiguration {
        val colorProvider = DoppelViewTypeColorProvider(
            ContextCompat.getColor(context, R.color.colorAccent)
        )
        colorProvider.setCornerRadius(context, 5)
        colorProvider.setShrinkage(context, 3)
        return DoppelConfigurationBuilder(context)
            .withBackgroundProvider(colorProvider)
            .parentViewInclusive(true)
            .build()
    }

    fun getCoinListRedrawRequired() = preferences.getBoolean(COIN_REDRAW_REQUIRED, false)

    fun getTrackerListRedrawRequired() = preferences.getBoolean(TRACKER_REDRAW_REQUIRED, false)

    fun setTrackerListRedrawRequired(required: Boolean) =
        putBoolean(TRACKER_REDRAW_REQUIRED, required)

    fun setCoinListRedrawRequired(required: Boolean) = putBoolean(COIN_REDRAW_REQUIRED, required)

    fun setSort(sort: Int) = putInt(SORT_BY, sort)

    fun setMarketLimit(limit: Int) = putInt(MARKET_LIMIT, limit)

    fun setValuesHidden(hidden: Boolean) = putBoolean(HIDDEN_VALUES, hidden)

    fun setIconPack(key: Int) = putInt(ICON_PACK, key)

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

    private val marketDefaults = listOf(50f, 15f, 5f, -5f, -15f, -50f)
    private val trackerDefaults = listOf(1.5f, 0.5f, 0.15f, -0.1f, -0.25f, -0.5f)

    fun getValueForMarketThreshold(index: Int): Float {
        val key = MARKET_THRESHOLDS + index
        val default = marketDefaults[index]
        return preferences.getFloat(key, default)
    }

    fun getValueForTrackerThreshold(index: Int): Float {
        val key = TRACKER_THRESHOLDS + index
        val default = trackerDefaults[index]
        return preferences.getFloat(key, default)
    }

    @SuppressLint("ApplySharedPref")
    fun setValueForMarketThreshold(index: Int, value: Float) {
        val key = MARKET_THRESHOLDS + index
        preferences.edit().putFloat(key, value).commit()
    }

    @SuppressLint("ApplySharedPref")
    fun setValueForTrackerThreshold(index: Int, value: Float) {
        val key = TRACKER_THRESHOLDS + index
        preferences.edit().putFloat(key, value).commit()
    }
}
