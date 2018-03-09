package makes.flint.alt.ui.constraintui.coinlist

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart.CoinDetailChart
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary.CoinDetailSummary
import makes.flint.alt.ui.market.coinlist.CoinListAdapter
import makes.flint.alt.ui.market.coinlist.CoinListAdapterContractView

/**
 * CoinListFragment
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class CoinListFragment : BaseFragment(), CoinListContractView {

    private lateinit var views: CoinListViewHolder
    private lateinit var coinListPresenter: CoinListContractPresenter
    private lateinit var coinListAdapter: CoinListAdapterContractView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_coin_list, container, false)
        coinListPresenter = getPresenterComponent().provideCoinListPresenter()
        coinListPresenter.attachView(this)
        this.attachPresenter(coinListPresenter)
        view?.let {
            this.views = CoinListViewHolder(view)
        }
        coinListPresenter.initialise()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        coinListAdapter.onDestroy()
    }

    override fun initialiseListAdapter() {
        val presenterComponent = getPresenterComponent()
        val coinListAdapter = CoinListAdapter(presenterComponent)
        val layoutManager = LinearLayoutManager(context)
        views.coinList.layoutManager = layoutManager
        views.coinList.adapter = coinListAdapter
        this.coinListAdapter = coinListAdapter
        coinListAdapter.onCoinSelected().subscribe { coinSymbol -> coinListPresenter.onCoinSelected(coinSymbol) }
    }

    override fun initialiseSwipeRefreshListener() {
        val refreshColour = ContextCompat.getColor(context, R.color.colorAccent)
        views.swipeRefresh.setColorSchemeColors(refreshColour)
        views.swipeRefresh.setOnRefreshListener {
            coinListPresenter.refresh()
        }
    }

    override fun initialiseScrollListener() {
        views.coinList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var yPosition = 0
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                yPosition += dy
                handleScrollChange(yPosition)
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun showDialogForCoin(coinSymbol: String) {
        val fragmentManager = activity.supportFragmentManager
        val newCoinDetail = CoinDetailSummary.getInstanceFor(coinSymbol)
        val coinChart = CoinDetailChart.getInstanceFor(coinSymbol)
        fragmentManager
                .beginTransaction()
                .replace(R.id.pop_frame_b, newCoinDetail)
                .replace(R.id.frame_b, coinChart)
                .commit()
    }

    override fun initialiseFABonClick() {
        views.goToTopFAB.setOnClickListener {
            views.coinList.smoothScrollToPosition(0)
        }
    }

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