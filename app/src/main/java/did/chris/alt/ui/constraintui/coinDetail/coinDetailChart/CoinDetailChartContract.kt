package did.chris.alt.ui.constraintui.coinDetail.coinDetailChart

import did.chris.alt.base.BaseContractPresenter
import did.chris.alt.base.BaseContractView
import did.chris.alt.data.response.histoResponse.HistoricalDataUnitResponse

interface CoinDetailChartContractView : BaseContractView {

    // Functions
    fun displayChart(currentData: Array<HistoricalDataUnitResponse>)
    fun setChartChangeListener()
    fun displayHighlightTime(formattedTime: String?)
    fun displayHighlightValue(formattedAmount: String)
    fun hideHighlightedData()
}

interface CoinDetailChartContractPresenter : BaseContractPresenter<CoinDetailChartContractView> {

    // Functions
    fun getHistoricalDataFor(chartResolution: Int)
    fun valueSelected(dataPoint: HistoricalDataUnitResponse)
     fun setCoinSymbol(coinSymbol: String)
}