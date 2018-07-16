package did.chris.alt.ui.dyor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import did.chris.alt.R
import did.chris.alt.base.BaseBottomSheetDialogFragment
import did.chris.alt.data.coinListItem.CoinListItem
import java.net.URLEncoder

// Const Properties
private const val DYOR_COIN_KEY = "dyor"

class DYORBottomSheet : BaseBottomSheetDialogFragment(), DYORBottomSheetContractView {

    // Companion
    companion object {
        fun getInstanceFor(coinSymbol: String): DYORBottomSheet {
            val bundle = Bundle()
            bundle.putString(DYOR_COIN_KEY, coinSymbol)
            val bottomSheet = DYORBottomSheet()
            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    // Properties
    private lateinit var bottomSheetPresenter: DYORBottomSheetContractPresenter
    private lateinit var views: DYORBottomSheetViewHolder

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSheetPresenter = getPresenterComponent().provideDYORBottomSheetPresenter()
        attachPresenter(bottomSheetPresenter)
        bottomSheetPresenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottomsheet_dyor, container, false)
        views = DYORBottomSheetViewHolder(view)
        val coinSymbol = arguments?.getString(DYOR_COIN_KEY)
        bottomSheetPresenter.setCoinSymbol(coinSymbol)
        bottomSheetPresenter.initialise()
        return view
    }

    // Overrides
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(stringId: Int?) {
    }

    override fun initialiseGoogleButton(coin: CoinListItem?) {
        views.googleButton.setOnClickListener {
            val queryString = "Cryptocurrency ${coin?.name} information"
            val query = URLEncoder.encode(queryString, "utf-8")
            val url = "http://www.google.com/search?q=$query"
            startBrowser(url)
        }
    }

    override fun initialiseFacebookButton(coin: CoinListItem?) {
        views.facebookButton.setOnClickListener {
            val query = URLEncoder.encode(coin?.name, "utf-8")
            val url = "https://www.facebook.com/search/top/?q=$query"
            startBrowser(url)
        }
    }

    override fun initialiseGitHubButton(coin: CoinListItem?) {
        views.githubButton.setOnClickListener {
            val query = URLEncoder.encode(coin?.name, "utf-8")
            val url = "https://github.com/search?q=$query"
            startBrowser(url)
        }
    }

    override fun initialiseCoinMarketCapButton(coin: CoinListItem?) {
        views.cmcButton.setOnClickListener {
            val compressedName = coin?.name?.replace("\\s".toRegex(), "-")
            val query = URLEncoder.encode(compressedName, "utf-8")
            val url = "https://coinmarketcap.com/currencies/$query"
            startBrowser(url)
        }
    }

    // Private Functions
    private fun startBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
