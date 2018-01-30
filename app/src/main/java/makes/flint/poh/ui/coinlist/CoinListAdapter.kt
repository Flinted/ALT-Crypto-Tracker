package makes.flint.poh.ui.coinlist

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import makes.flint.poh.R
import makes.flint.poh.configuration.IndicatorCustomiser
import makes.flint.poh.data.coinListItem.CoinListItem
import makes.flint.poh.injection.components.PresenterComponent
import makes.flint.poh.ui.market.MarketFragment
import rx.subjects.PublishSubject

/**
 * CoinListAdapter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class CoinListAdapter(presenterComponent: PresenterComponent,
                      private var marketFragment: MarketFragment)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), CoinListAdapterContractView, Filterable {

    // Public Properties
    override var coinList: MutableList<CoinListItem> = mutableListOf()
        set(value) {
            field = value
            filteredCoins = coinList
        }

    // Internal Properties
    internal var filteredCoins = coinList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // Private Properties
    private var presenter = presenterComponent.provideCoinListAdapterPresenter()

    private var syncUpdate: PublishSubject<String?> = PublishSubject.create()

    private var indicatorCustomiser = IndicatorCustomiser()

    // Lifecycle
    init {
        presenter.attachView(this)
        presenter.initialise()
    }

    override fun getItemCount(): Int {
        return filteredCoins.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val layout = when (viewType) {
            2 -> R.layout.coin_list_row_favourite
            else -> R.layout.coin_list_row
        }
        val itemView = layoutInflater.inflate(layout, parent, false)
        return CoinListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val coinListViewHolder = holder as CoinListViewHolder
        val coin = filteredCoins[position]
        val context = coinListViewHolder.oneHourIndicator.context
        initialiseCoinViews(coin, coinListViewHolder)
        initialise1HourViews(coin, coinListViewHolder, context)
        initialise24HourViews(coin, coinListViewHolder, context)
        initialise7DayViews(coin, coinListViewHolder, context)
        setOnClickListener(coinListViewHolder.card, coin, position)
    }

    override fun onSyncCompleted() = syncUpdate.asObservable()

    private fun initialiseCoinViews(coin: CoinListItem, coinListViewHolder: CoinListViewHolder) {
        coinListViewHolder.name.text = coin.name
        coinListViewHolder.ticker.text = coin.symbolFormatted()
        coinListViewHolder.price.text = coin.priceData.priceUSDFormatted()
        coinListViewHolder.rank.text = coin.rank.toString()
        coinListViewHolder.stabilisedPrice.text = coin.priceData.stabilisedPrice
    }

    private fun initialise1HourViews(coin: CoinListItem, holder: CoinListViewHolder, context: Context) {
        holder.oneHourChange.text = coin.changeData.change1HFormatted()
        val status = coin.changeData.status1H
        val icon = ContextCompat.getDrawable(context, indicatorCustomiser.getIcon(status))
        val colour = ContextCompat.getColor(context, indicatorCustomiser.getColor(status))
        holder.oneHourIndicator.setImageDrawable(icon)
        holder.oneHourChange.setTextColor(colour)
    }

    private fun initialise24HourViews(coin: CoinListItem, holder: CoinListViewHolder, context: Context) {
        holder.twentyFourHourChange.text = coin.changeData.change24HFormatted()
        val status = coin.changeData.status24H
        val icon = ContextCompat.getDrawable(context, indicatorCustomiser.getIcon(status))
        val colour = ContextCompat.getColor(context, indicatorCustomiser.getColor(status))
        holder.twentyFourHourIndicator.setImageDrawable(icon)
        holder.twentyFourHourChange.setTextColor(colour)
    }

    private fun initialise7DayViews(coin: CoinListItem, holder: CoinListViewHolder, context: Context) {
        holder.sevenDayChange.text = coin.changeData.change7DFormatted()
        val status = coin.changeData.status7D
        val icon = ContextCompat.getDrawable(context, indicatorCustomiser.getIcon(status))
        val colour = ContextCompat.getColor(context, indicatorCustomiser.getColor(status))
        holder.sevenDayIndicator.setImageDrawable(icon)
        holder.sevenDayChange.setTextColor(colour)
    }

    private fun setOnClickListener(card: CardView, coin: CoinListItem, position: Int) {
        card.setOnClickListener {
            println("${coin.name} Clicked!")
        }
        card.setOnLongClickListener {
            presenter.onLongPress(coin, position)
        }
    }

    override fun showLoading() = marketFragment.showLoading()

    override fun hideLoading() = marketFragment.hideLoading()

    override fun showError(stringId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshList() = presenter.initialise()

    override fun filterFor(input: String) = filter.filter(input)

    override fun getFilter(): Filter {
        val callback = makeCoinFilterCallback()
        return CoinFilter(coinList, callback)
    }

    private fun makeCoinFilterCallback(): CoinFilterCallback {
        return object : CoinFilterCallback {
            override fun publishResults(filteredList: MutableList<CoinListItem>) {
                filteredCoins = filteredList
            }
        }
    }

    override fun updateLastSync(lastSync: String) = syncUpdate.onNext(lastSync)

    override fun notRefreshed() = syncUpdate.onNext(null)

    override fun itemChangedAt(position: Int) = notifyItemChanged(position)

    override fun getItemViewType(position: Int): Int {
        return when {
            filteredCoins[position].isFavourite -> 2
            else -> super.getItemViewType(position)
        }
    }
}