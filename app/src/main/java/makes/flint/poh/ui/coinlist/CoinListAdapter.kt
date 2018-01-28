package makes.flint.poh.ui.coinlist

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import makes.flint.poh.R
import makes.flint.poh.data.coinListItem.*
import makes.flint.poh.injection.components.PresenterComponent
import makes.flint.poh.ui.market.MarketFragment

/**
 * CoinListAdapter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListAdapter(presenterComponent: PresenterComponent, private var marketFragment: MarketFragment)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), CoinListAdapterContractView {

    // Public Properties
    override var coinList: MutableList<CoinListItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // Private Properties
    private var presenter = presenterComponent.provideCoinListAdapterPresenter()

    // Lifecycle
    init {
        presenter.attachView(this)
        presenter.initialise()
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemView = layoutInflater.inflate(R.layout.coin_list_row, parent, false)
        return CoinListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val coinListViewHolder = holder as CoinListViewHolder
        val coin = coinList[position]
        coinListViewHolder.name.text = coin.name
        coinListViewHolder.ticker.text = "(${coin.symbol})"
        coinListViewHolder.price.text = coin.priceData.priceUSD?.toPlainString()
        coinListViewHolder.rank.text = coin.rank.toString()
        coinListViewHolder.stabilisedPrice.text = coin.priceData.stabilisedPrice
        coinListViewHolder.oneHourChange.text = coin.changeData.percentChange1H.toString()
        coinListViewHolder.twentyFourHourChange.text = coin.changeData.percentChange24H.toString()
        coinListViewHolder.sevenDayChange.text = coin.changeData.percentChange7D.toString()
        val context = coinListViewHolder.oneHourIndicator.context
        setIndicators(coin, coinListViewHolder, context)
    }

    private fun setIndicators(coin: CoinListItem, coinListViewHolder: CoinListViewHolder, context: Context) {
        val oneHour = coin.changeData.status1H
        val twentyFourHour = coin.changeData.status24H
        val sevenDay = coin.changeData.status7D
        val oneHourIcon = ContextCompat.getDrawable(context, getIcon(oneHour))
        val twentyFourHourIcon = ContextCompat.getDrawable(context, getIcon(twentyFourHour))
        val sevenDayIcon = ContextCompat.getDrawable(context, getIcon(sevenDay))
        val oneHourColour = ContextCompat.getColor(context, getColour(oneHour))
        val twentyFourHourColour = ContextCompat.getColor(context, getColour(twentyFourHour))
        val sevenDayColour = ContextCompat.getColor(context, getColour(sevenDay))
        coinListViewHolder.oneHourIndicator.setImageDrawable(oneHourIcon)
        coinListViewHolder.twentyFourHourIndicator.setImageDrawable(twentyFourHourIcon)
        coinListViewHolder.sevenDayIndicator.setImageDrawable(sevenDayIcon)
        coinListViewHolder.oneHourChange.setTextColor(oneHourColour)
        coinListViewHolder.twentyFourHourChange.setTextColor(twentyFourHourColour)
        coinListViewHolder.sevenDayChange.setTextColor(sevenDayColour)
    }

    private fun getColour(status: Int): Int {
        return when (status) {
            CHANGE_DOWN_MODERATE, CHANGE_DOWN_SIGNIFICANT, CHANGE_DOWN_EXTREME, CHANGE_STATIC_NEGATIVE
            -> R.color.colorRed
            CHANGE_UNKNOWN -> R.color.colorOffBlack
            else -> R.color.colorGreen
        }
    }

    private fun getIcon(status: Int): Int {
        return when (status) {
            CHANGE_UP_EXTREME -> R.drawable.ic_up_extreme_24dp
            CHANGE_UP_MODERATE -> R.drawable.ic_up_moderate_24dp
            CHANGE_UP_SIGNIFICANT -> R.drawable.ic_up_significant_24dp
            CHANGE_DOWN_MODERATE -> R.drawable.ic_down_moderate_24dp
            CHANGE_DOWN_SIGNIFICANT -> R.drawable.ic_down_significant_24dp
            CHANGE_DOWN_EXTREME -> R.drawable.ic_down_extreme_24dp
            CHANGE_UNKNOWN -> R.drawable.ic_change_unknown_24dp
            else -> R.drawable.ic_movement_static_24dp
        }
    }

    override fun showLoading() {
        marketFragment.showLoading()
    }

    override fun hideLoading() {
        marketFragment.hideLoading()
    }

    override fun showError(stringId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshList() {
        presenter.initialise()
    }
}