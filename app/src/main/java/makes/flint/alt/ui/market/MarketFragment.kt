package makes.flint.alt.ui.market

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.data.response.marketSummary.MarketSummaryResponse
import makes.flint.alt.ui.interfaces.*
import makes.flint.alt.ui.main.MainActivity
import makes.flint.alt.ui.market.coinDetail.CoinDetailDialog
import makes.flint.alt.ui.market.coinlist.CoinListAdapter
import makes.flint.alt.ui.market.coinlist.CoinListAdapterContractView

/**
 * MarketFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class MarketFragment : BaseFragment(), MarketContractView, FilterView {

    // View Bindings

    private lateinit var coinListRecyclerView: RecyclerView
    private lateinit var progressSpinner: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var goToTopFAB: FloatingActionButton
    private lateinit var lastSyncTime: TextView
    private lateinit var currentSort: TextView
    private lateinit var marketSummary: ConstraintLayout
    private lateinit var marketCapTextView: TextView
    private lateinit var vol24HTextView: TextView
    private lateinit var market1DTitleTextView: TextView
    private lateinit var market1DValueTextView: TextView
    private lateinit var market1WTitleTextView: TextView
    private lateinit var market1WValueTextView: TextView

    private fun bindViews(view: View?) {
        view ?: return
        this.progressSpinner = view.findViewById(R.id.market_progress_spinner)
        this.coinListRecyclerView = view.findViewById(R.id.market_recycler_view)
        this.swipeRefresh = view.findViewById(R.id.coin_list_refresh_layout)
        this.goToTopFAB = view.findViewById(R.id.coin_list_FAB)
        this.lastSyncTime = view.findViewById(R.id.bottom_ticker_right)
        this.currentSort = view.findViewById(R.id.bottom_ticker_left)
        this.marketSummary = view.findViewById(R.id.market_top_ticker)
        this.marketCapTextView = view.findViewById(R.id.market_summary_market_cap)
        this.vol24HTextView = view.findViewById(R.id.market_summary_vol_24H)
        this.market1DTitleTextView = view.findViewById(R.id.market_summary_1D_title)
        this.market1WTitleTextView = view.findViewById(R.id.market_summary_1W_title)
        this.market1DValueTextView = view.findViewById(R.id.market_summary_1D)
        this.market1WValueTextView = view.findViewById(R.id.market_summary_1W)
    }
    // Properties

    private lateinit var marketPresenter: MarketContractPresenter
    private lateinit var coinListAdapter: CoinListAdapterContractView

    // Lifecycle

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_market, container, false)
        marketPresenter = getPresenterComponent().provideMarketPresenter()
        marketPresenter.attachView(this)
        this.attachPresenter(marketPresenter)
        bindViews(view)
        marketPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        marketPresenter.onDestroy()
        coinListAdapter.onDestroy()
    }

    // Overridden Functions

    override fun initialiseListAdapter() {
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent)
        val layoutManager = LinearLayoutManager(context)
        coinListRecyclerView.layoutManager = layoutManager
        coinListRecyclerView.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
    }

    override fun initialiseSwipeRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        swipeRefresh.setColorSchemeColors(refreshColour)
        swipeRefresh.setOnRefreshListener {
            (activity as MainActivity).clearSearchTerms()
            marketPresenter.refresh()
        }
    }

    override fun initialiseScrollListener() {
        coinListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var yPosition = 0
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                yPosition += dy
                handleScrollChange(yPosition)
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun initialiseFABonClick() {
        goToTopFAB.setOnClickListener {
            coinListRecyclerView.smoothScrollToPosition(0)
        }
    }

    override fun initialiseAdapterListeners() {
        coinListAdapter.onCoinSelected().subscribe { coinSymbol -> marketPresenter.onCoinSelected(coinSymbol) }
        coinListAdapter.onSortTypeChanged().subscribe { sortId ->
            this.currentSort.text = getStringForSortId(sortId)
        }
    }

    private fun getStringForSortId(sortId: Int): String {
        val id = when (sortId) {
            SORT_RANK -> R.string.sort_menu_rank
            SORT_RANK_REV -> R.string.sort_menu_rank_reversed
            SORT_NAME -> R.string.sort_menu_alphabetical
            SORT_NAME_REV -> R.string.sort_menu_alphabetical_reversed
            SORT_ONE_HOUR -> R.string.sort_menu_1H
            SORT_ONE_HOUR_REV -> R.string.sort_menu_1H_reversed
            SORT_TWENTY_FOUR_HOUR -> R.string.sort_menu_24H
            SORT_TWENTY_FOUR_HOUR_REV -> R.string.sort_menu_24H_reversed
            SORT_SEVEN_DAY -> R.string.sort_menu_7D
            SORT_SEVEN_DAY_REV -> R.string.sort_menu_7D_reversed
            SORT_VOLUME -> R.string.sort_menu_vol
            else -> R.string.sort_menu_vol_reversed
        }
        return getString(id)
    }

    override fun hideProgressSpinner() {
        this.progressSpinner.visibility = View.GONE
        this.swipeRefresh.visibility = View.VISIBLE
    }

    override fun updateMarketSummary(marketSummary: MarketSummaryResponse) {
        val twentyFourHour = marketSummary.marketData.twentyFourHourAverageFormatted()
        val sevenDay = marketSummary.marketData.sevenDayAverageFormatted()
        val coins = marketSummary.marketData.numberItems
        this.market1DTitleTextView.text = context.getString(R.string.market_summary_title_1D, coins)
        this.market1WTitleTextView.text = context.getString(R.string.market_summary_title_1W, coins)
        this.market1DValueTextView.text = twentyFourHour
        this.market1WValueTextView.text = sevenDay
        this.marketCapTextView.text = marketSummary.marketCapUSDFormatted()
        this.vol24HTextView.text = marketSummary.volume24HUSDFormatted()
    }

    override fun updateLastSyncTime(lastSync: Long) {
        val formattedLastSync = getString(R.string.market_fragment_time_stamp, lastSync)
        lastSyncTime.text = formattedLastSync
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
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
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
        goToTopFAB.show()
    }

    private fun hideGoToTopFAB() {
        goToTopFAB.hide()
    }
}
