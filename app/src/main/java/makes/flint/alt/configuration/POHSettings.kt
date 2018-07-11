package makes.flint.alt.configuration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import makes.flint.alt.ui.interfaces.SORT_RANK
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
    fun getIconPack(): IconPack {
        val packKey = preferences.getInt(ICON_PACK, 0)
        return when (packKey) {
            0 -> DefaultIconPack()
            else -> ArrowIconPack()
        }
    }

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
}

object POHSettings {

    // Market Thresholds
    internal var changeUp3 = 50f
    internal var changeUp2 = 15f
    internal var changeUp1 = 5f
    internal var changeDown1 = -5f
    internal var changeDown2 = -15f
    internal var changeDown3 = -50f

    // Portfolio Thresholds

    internal var trackerChangeUp3 = 1.5f
    internal var trackerChangeUp2 = 0.1f
    internal var trackerChangeUp1 = 0.2f
    internal var trackerChangeDown1 = -0.2f
    internal var trackerChangeDown2 = -0.4f
    internal var trackerChangeDown3 = -0.6f
}
