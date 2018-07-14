package did.chris.alt.ui.constraintui.coinlist.coinListAdapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import did.chris.alt.R
import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.configuration.IndicatorCustomiser
import did.chris.alt.data.coinListItem.CoinListItem
import did.chris.alt.injection.components.PresenterComponent
import rx.subjects.PublishSubject

/**
 * CoinListAdapter
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class CoinListAdapter(presenterComponent: PresenterComponent) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), CoinListAdapterContractView, Filterable {

    // Properties
    override var coinList: MutableList<CoinListItem> = mutableListOf()
        set(value) {
            field = value
            filteredCoins = coinList
        }

    internal var filteredCoins = coinList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun updateIconPack() {
        indicatorCustomizer.updateIconPack(ALTSharedPreferences.getIconPack())
    }

    private var presenter = presenterComponent.provideCoinListAdapterPresenter()
    private var indicatorCustomizer = IndicatorCustomiser(ALTSharedPreferences.getIconPack())

    // RX Actions
    private val coinSelected: PublishSubject<String> = PublishSubject.create()

    override fun onCoinSelected() = coinSelected.asObservable()

    private val sortTypeChanged: PublishSubject<Int> = PublishSubject.create()
    override fun onSortTypeChanged() = sortTypeChanged.asObservable()

    // Lifecycle
    init {
        presenter.attachView(this)
        presenter.initialise()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layout = when (viewType) {
            2    -> R.layout.item_coin_list_favourite
            else -> R.layout.item_coin_list
        }
        val itemView = layoutInflater.inflate(layout, parent, false)
        return CoinListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val coinListViewHolder = holder as CoinListViewHolder
        val coin = filteredCoins[position]
        val context = coinListViewHolder.oneHourIndicator.context
        initialiseCoinViews(coin, coinListViewHolder)
        initialise1HourViews(coin, coinListViewHolder, context)
        initialise24HourViews(coin, coinListViewHolder, context)
        initialise7DayViews(coin, coinListViewHolder, context)
        setOnClickListener(coinListViewHolder.card, coin)
        setOnCheckedChangeListener(coinListViewHolder.favouriteIcon, coin, position)
        if (coin.symbol != "DOGE") {
            return
        }
        setDogeIcons(coinListViewHolder)
    }

    override fun onDestroy() = presenter.onDestroy()

    // Overrides

    override fun emitSortTypeChanged(sortId: Int) {
        sortTypeChanged.onNext(sortId)
    }

    override fun getItemCount() = filteredCoins.size

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(stringId: Int?) {}

    override fun filterFor(input: String) = filter.filter(input)

    override fun getFilter(): Filter {
        val callback = makeCoinFilterCallback()
        return CoinFilter(coinList, callback)
    }

    override fun isFiltered(): Boolean {
        return filteredCoins.size != coinList.size
    }

    private fun makeCoinFilterCallback(): CoinFilterCallback {
        return object : CoinFilterCallback {
            override fun publishResults(filteredList: MutableList<CoinListItem>) {
                filteredCoins = filteredList
            }
        }
    }

    override fun itemChangedAt(position: Int) = notifyItemChanged(position)

    override fun getItemViewType(position: Int): Int {
        return when {
            filteredCoins[position].isFavourite -> 2
            else                                -> super.getItemViewType(position)
        }
    }

    // Private Functions

    private fun setDogeIcons(holder: CoinListViewHolder) {
        val context = holder.card.context
        val doge = ContextCompat.getDrawable(context, R.drawable.ic_doge_up_24dp)
        holder.oneHourIndicator.setImageDrawable(doge)
        holder.twentyFourHourIndicator.setImageDrawable(doge)
        holder.sevenDayIndicator.setImageDrawable(doge)
    }

    private fun initialiseCoinViews(coin: CoinListItem, coinListViewHolder: CoinListViewHolder) {
        coinListViewHolder.name.text = coin.name
        coinListViewHolder.ticker.text = coin.symbolFormatted
        coinListViewHolder.price.text = coin.priceData.priceUSDFormatted
        if (!coin.isFavourite) {
            return
        }
        val context = coinListViewHolder.name.context
        coinListViewHolder.name.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
    }

    private fun initialise1HourViews(
        coin: CoinListItem,
        holder: CoinListViewHolder,
        context: Context
    ) {
        holder.oneHourChange.text = coin.changeData.change1HFormatted()
        val status = coin.changeData.status1H
        val icon = ContextCompat.getDrawable(context, indicatorCustomizer.getIcon(status))
        val colour = ContextCompat.getColor(context, indicatorCustomizer.getColor(status))
        holder.oneHourIndicator.setImageDrawable(icon)
        holder.oneHourChange.setTextColor(colour)
    }

    private fun initialise24HourViews(
        coin: CoinListItem,
        holder: CoinListViewHolder,
        context: Context
    ) {
        holder.twentyFourHourChange.text = coin.changeData.change24HFormatted()
        val status = coin.changeData.status24H
        val icon = ContextCompat.getDrawable(context, indicatorCustomizer.getIcon(status))
        val colour = ContextCompat.getColor(context, indicatorCustomizer.getColor(status))
        holder.twentyFourHourIndicator.setImageDrawable(icon)
        holder.twentyFourHourChange.setTextColor(colour)
    }

    private fun initialise7DayViews(
        coin: CoinListItem,
        holder: CoinListViewHolder,
        context: Context
    ) {
        holder.sevenDayChange.text = coin.changeData.change7DFormatted()
        val status = coin.changeData.status7D
        val icon = ContextCompat.getDrawable(context, indicatorCustomizer.getIcon(status))
        val colour = ContextCompat.getColor(context, indicatorCustomizer.getColor(status))
        holder.sevenDayIndicator.setImageDrawable(icon)
        holder.sevenDayChange.setTextColor(colour)
    }

    private fun setOnClickListener(card: ConstraintLayout, coin: CoinListItem) {
        card.setOnClickListener {
            coinSelected.onNext(coin.symbol)
        }
    }

    private fun setOnCheckedChangeListener(
        favourite: ImageView,
        coin: CoinListItem,
        position: Int
    ) {
        favourite.setOnClickListener {
            val isFavourite = it.tag == "checked"
            presenter.onFavouriteStateChanged(isFavourite, coin, position)
        }
    }
}
