package did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import did.chris.alt.R
import did.chris.alt.base.BaseFragment
import did.chris.alt.configuration.ALTSharedPreferences
import did.chris.alt.data.coinListItem.CoinListItem
import did.chris.alt.errors.ErrorHandler
import did.chris.alt.layoutCoordination.home
import did.chris.alt.ui.constraintui.addCoin.AddCoinDialogFragment
import did.chris.alt.ui.constraintui.layoutCoordinator.LayoutCoordinatable
import did.chris.alt.ui.dyor.DYORBottomSheet

const val COIN_SYMBOL_KEY = "CoinSymbolKey"

class CoinDetailSummaryFragment : BaseFragment(), CoinDetailContractView {

    // Companion
    companion object {
        fun getInstanceFor(coinSymbol: String): CoinDetailSummaryFragment {
            val coinDetail = CoinDetailSummaryFragment()
            val bundle = Bundle()
            bundle.putString(COIN_SYMBOL_KEY, coinSymbol)
            coinDetail.arguments = bundle
            return coinDetail
        }
    }

    // Properties
    private lateinit var coinDetailPresenter: CoinDetailContractPresenter
    private lateinit var views: CoinDetailSummaryViewHolder

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinDetailPresenter = getPresenterComponent().provideCoinDetailPresenter()
        coinDetailPresenter.attachView(this)
        this.attachPresenter(coinDetailPresenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coin_detail, container, false)
        this.views = CoinDetailSummaryViewHolder(view)
        val coinSymbol = arguments?.getString(COIN_SYMBOL_KEY)
        coinDetailPresenter.setCoinSymbol(coinSymbol)
        coinDetailPresenter.initialise()
        initialiseBackButton()
        return view
    }

    // Overrides
    override fun displayCoinDetail(coin: CoinListItem?) {
        views.coinName.text = coin?.name
        views.coinSymbol.text = coin?.symbolFormatted
        views.rank.text = coin?.rank.toString()
        views.sortedRankTitle.text =
                getString(
                    R.string.dialog_coinDetail_title_sorted_rank,
                    context?.resources?.getStringArray(R.array.sort_options)?.get(
                        ALTSharedPreferences.getSort()
                    )
                )
        views.sortedRank.text = coin?.sortedRank.toString()
        views.priceFiat.text =
                getString(R.string.title_priceUSD, coin?.priceData?.priceUSDFormatted)
        views.priceBTC.text =
                getString(R.string.title_priceBTC, coin?.priceData?.priceBTCFormatted)
        views.priceBillionCoin.text =
                getString(
                    R.string.dialog_coinDetail_one_billion_coin,
                    coin?.priceData?.stabilisedPrice
                )
        views.volume24H.text = coin?.volume24HourFormatted
        views.supplyAvailable.text = coin?.availableSupplyFormatted
        views.supplyTotal.text = coin?.totalSupplyFormatted
        views.marketCap.text = coin?.marketCapFormatted()
        checkDogeMuchWow(coin?.symbol)
    }

    override fun initialiseDYORButton(coinSymbol: String) {
        views.dyorButton.setOnClickListener {
            val bottomSheetFragment = DYORBottomSheet.getInstanceFor(coinSymbol)
            bottomSheetFragment.show(activity?.supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    override fun showError(stringId: Int?) = ErrorHandler.showError(activity, stringId)

    override fun initialiseAddEntryButton(searchKey: String) {
        views.addEntryButton.setOnClickListener {
            val fragment = AddCoinDialogFragment.createForAsset(searchKey)
            val fragmentManager = activity?.fragmentManager
            val shownCoinDetail = fragmentManager?.findFragmentByTag("AddCoinDialog")
            shownCoinDetail?.let { shownDialog ->
                fragmentManager.beginTransaction().remove(shownDialog).commit()
            }
            fragment.show(fragmentManager, "AddCoinDialog")
        }
    }

    // Private Functions

    private fun checkDogeMuchWow(coinSymbol: String?) {
        if (coinSymbol == "DOGE") {
            views.priceBTCTitle.text = getString(R.string.dialog_coinDetail_title_doge_price)
            views.priceBTC.text = getString(R.string.dialog_coinDetail_placeholder_doge_price)
        }
    }

    private fun initialiseBackButton() {
        views.backButton.setOnClickListener {
            (activity as LayoutCoordinatable).updateLayout(home)
        }
    }
}
