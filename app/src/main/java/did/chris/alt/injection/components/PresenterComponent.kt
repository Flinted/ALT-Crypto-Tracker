package did.chris.alt.injection.components

import dagger.Component
import did.chris.alt.injection.modules.DataModule
import did.chris.alt.injection.modules.PresenterModule
import did.chris.alt.ui.constraintui.addCoin.AddCoinPresenter
import did.chris.alt.ui.constraintui.coinDetail.coinDetailChart.CoinDetailChartPresenter
import did.chris.alt.ui.constraintui.coinDetail.coinDetailSummary.CoinDetailPresenter
import did.chris.alt.ui.constraintui.coinlist.CoinListPresenter
import did.chris.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapterPresenter
import did.chris.alt.ui.constraintui.layoutCoordinator.LayoutPresenter
import did.chris.alt.ui.constraintui.trackerBarChart.TrackerBarChartPresenter
import did.chris.alt.ui.constraintui.trackerEntryDetail.TrackerDetailDialogPresenter
import did.chris.alt.ui.constraintui.trackerList.TrackerListPresenter
import did.chris.alt.ui.constraintui.trackerList.trackerListAdapter.TrackerAdapterPresenter
import did.chris.alt.ui.constraintui.trackerSummary.PortfolioSummaryPresenter
import did.chris.alt.ui.dyor.DYORBottomSheetPresenter
import did.chris.alt.ui.settings.SettingsPresenter
import did.chris.alt.ui.tracker.trackerEntryDialog.transactionsList.TransactionsAdapterPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(PresenterModule::class, DataModule::class))
interface PresenterComponent {

    // Functions
    fun provideLayoutPresenter(): LayoutPresenter

    fun provideCoinListPresenter(): CoinListPresenter

    fun provideCoinChartPresenter(): CoinDetailChartPresenter

    fun provideCoinDetailPresenter(): CoinDetailPresenter

    fun provideTrackerChartPresenter(): TrackerBarChartPresenter

    fun provideCoinListAdapterPresenter(): CoinListAdapterPresenter

    fun provideTrackerPresenter(): TrackerListPresenter

    fun provideTrackerAdapterPresenter(): TrackerAdapterPresenter

    fun provideAddCoinDialogPresenter(): AddCoinPresenter

    fun provideTrackerEntryDialogPresenter(): TrackerDetailDialogPresenter

    fun provideTransactionsAdapterPresenter(): TransactionsAdapterPresenter

    fun provideSummaryPresenter(): PortfolioSummaryPresenter

    fun provideSettingsPresenter(): SettingsPresenter

    fun provideDYORBottomSheetPresenter(): DYORBottomSheetPresenter

}
