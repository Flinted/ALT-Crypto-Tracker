package makes.flint.alt.ui.market

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.ui.interfaces.FilterView
import makes.flint.alt.ui.main.MainActivity
import makes.flint.alt.ui.market.coinDetail.CoinDetailDialog
import makes.flint.alt.ui.market.coinlist.CoinListAdapter
import makes.flint.alt.ui.market.coinlist.CoinListAdapterContractView
import makes.flint.alt.ui.market.extensions.getStringIdFor

/**
 * MarketFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketFragment : BaseFragment(), MarketContractView, FilterView {

    // Properties

    private lateinit var views: MarketFragmentViewHolder
    private lateinit var marketPresenter: MarketContractPresenter
    private lateinit var coinListAdapter: CoinListAdapterContractView

    // Lifecycle

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_market, container, false)
        marketPresenter = getPresenterComponent().provideMarketPresenter()
        marketPresenter.attachView(this)
        this.attachPresenter(marketPresenter)
        view?.let {
            this.views = MarketFragmentViewHolder(view)
        }
        marketPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        marketPresenter.onDestroy()
        coinListAdapter.onDestroy()
    }

    // Overrides

    override fun initialiseListAdapter() {
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent)
        val layoutManager = LinearLayoutManager(context)
        views.coinListRecyclerView.layoutManager = layoutManager
        views.coinListRecyclerView.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
    }

    override fun initialiseSwipeRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        views.swipeRefresh.setColorSchemeColors(refreshColour)
        views.swipeRefresh.setOnRefreshListener {
            (activity as MainActivity).clearSearchTerms()
            marketPresenter.refresh()
        }
    }

    override fun initialiseScrollListener() {
        views.coinListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var yPosition = 0
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                yPosition += dy
                handleScrollChange(yPosition)
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun initialiseFABonClick() {
        views.goToTopFAB.setOnClickListener {
            views.coinListRecyclerView.smoothScrollToPosition(0)
        }
    }

    override fun initialiseAdapterListeners() {
        coinListAdapter.onCoinSelected().subscribe { coinSymbol -> marketPresenter.onCoinSelected(coinSymbol) }
        coinListAdapter.onSortTypeChanged().subscribe { sortId ->
            views.currentSort.text = getStringForSortId(sortId)
        }
    }

    private fun getStringForSortId(sortId: Int): String {
        val id = this.getStringIdFor(sortId)
        val sortType = getString(id)
        val title = getString(R.string.ticker_bottom_sort_type)
        return String.format(title, sortType)
    }

    override fun hideProgressSpinner() {
        views.progressSpinner.visibility = View.GONE
        views.swipeRefresh.visibility = View.VISIBLE
    }

    override fun updateMarketSummary(marketSummary: MarketSummaryResponse) {
        val twentyFourHour = marketSummary.marketData.twentyFourHourAverageFormatted()
        val sevenDay = marketSummary.marketData.sevenDayAverageFormatted()
        val coins = marketSummary.marketData.numberItems
        views.market1DTitleTextView.text = context.getString(R.string.ticker_top_title_1d, coins)
        views.market1WTitleTextView.text = context.getString(R.string.ticker_top_title_1W, coins)
        views.market1DValueTextView.text = twentyFourHour
        views.market1WValueTextView.text = sevenDay
        views.marketCapTextView.text = marketSummary.marketCapUSDFormatted()
        views.vol24HTextView.text = marketSummary.volume24HUSDFormatted()
    }

    override fun updateLastSyncTime(lastSync: String) {
        views.lastSyncTime.text = String.format(getString(R.string.ticker_bottom_time_stamp), lastSync)
    }

    override fun showDialogForCoin(coinSymbol: String) {
        val fragmentManager = activity.fragmentManager
        val shownCoinDetail = fragmentManager.findFragmentByTag("CoinDetailDialog")
        shownCoinDetail?.let {
            fragmentManager.beginTransaction().remove(it).commit()
        }
        val newCoinDetail = CoinDetailDialog.getInstanceFor(coinSymbol)
        newCoinDetail.show(fragmentManager, "CoinDetailDialog")
    }

    override fun showLoading() {
        views.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        views.swipeRefresh.isRefreshing = false
    }

    override fun filterFor(input: String) {
        coinListAdapter.filterFor(input)
    }

    // Private Functions

    private fun handleScrollChange(yPosition: Int) {
        if (yPosition < 2500) {
            hideGoToTopFAB()
            return
        }
        showGoToTopFAB()
    }

    private fun showGoToTopFAB() {
        views.goToTopFAB.show()
    }

    private fun hideGoToTopFAB() {
        views.goToTopFAB.hide()
    }
}
