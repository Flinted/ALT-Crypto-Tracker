package makes.flint.poh.coinlist

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import makes.flint.poh.R
import makes.flint.poh.data.response.coinSummary.*
import makes.flint.poh.injection.components.PresenterComponent

/**
 * CoinListAdapter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListAdapter(presenterComponent: PresenterComponent) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), CoinListAdapterContractView {

    // Public Properties
    override var coinList: MutableList<SummaryCoinResponse> = mutableListOf()
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
        coinListViewHolder.ticker.text = coin.symbol
        coinListViewHolder.price.text = coin.priceUSD
        coinListViewHolder.rank.text = coin.rank
        coinListViewHolder.stabilisedPrice.text = coin.stabilisedPrice()
        coinListViewHolder.oneHourChange.text = coin.percentChange1h
        coinListViewHolder.twentyFourHourChange.text = coin.percentChange24h
        coinListViewHolder.sevenDayChange.text = coin.percentChange7d
        val context = coinListViewHolder.oneHourIndicator.context
        setIndicators(coin, coinListViewHolder, context)
    }

    private fun setIndicators(coin: SummaryCoinResponse, coinListViewHolder: CoinListViewHolder, context: Context) {
        val oneHour = coin.oneHourStatus()
        val twentyFourHour = coin.twentyFourHourStatus()
        val sevenDay = coin.sevenDayStatus()
        val oneHourIcon = ContextCompat.getDrawable(context, getIcon(oneHour))
        val twentyFourHourIcon = ContextCompat.getDrawable(context, getIcon(twentyFourHour))
        val sevenDayIcon = ContextCompat.getDrawable(context, getIcon(sevenDay))
        coinListViewHolder.oneHourIndicator.setImageDrawable(oneHourIcon)
        coinListViewHolder.twentyFourHourIndicator.setImageDrawable(twentyFourHourIcon)
        coinListViewHolder.sevenDayIndicator.setImageDrawable(sevenDayIcon)
    }

    private fun getIcon(status: Int): Int {
        return when (status) {
            CHANGE_UP_MODERATE -> R.drawable.ic_up_moderate_black_24dp
            CHANGE_UP_SIGNIFICANT -> R.drawable.ic_up_significant_black_24dp
            CHANGE_DOWN_MODERATE -> R.drawable.ic_down_moderate_black_24dp
            CHANGE_DOWN_SIGNIFICANT -> R.drawable.ic_down_significant_black_24dp
            else -> R.drawable.ic_static_black_24dp
        }
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(stringId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}