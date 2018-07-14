package makes.flint.alt.injection.components

import dagger.Component
import makes.flint.alt.injection.modules.DataModule
import makes.flint.alt.injection.modules.PresenterModule
import makes.flint.alt.ui.constraintui.addCoin.AddCoinPresenter
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart.CoinDetailChartPresenter
import makes.flint.alt.ui.constraintui.coinDetail.coinDetailSummary.CoinDetailPresenter
import makes.flint.alt.ui.constraintui.coinlist.CoinListPresenter
import makes.flint.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapterPresenter
import makes.flint.alt.ui.constraintui.layoutCoordinator.LayoutPresenter
import makes.flint.alt.ui.constraintui.trackerBarChart.TrackerBarChartPresenter
import makes.flint.alt.ui.constraintui.trackerEntryDetail.TrackerDetailDialogPresenter
import makes.flint.alt.ui.constraintui.trackerList.TrackerListPresenter
import makes.flint.alt.ui.constraintui.trackerList.trackerListAdapter.TrackerAdapterPresenter
import makes.flint.alt.ui.constraintui.trackerSummary.PortfolioSummaryPresenter
import makes.flint.alt.ui.settings.SettingsPresenter
import makes.flint.alt.ui.tracker.trackerEntryDialog.transactionsList.TransactionsAdapterPresenter
import javax.inject.Singleton

/**
 * PresenterComponent
 * Copyright © 2018 ChrisDidThis.. All rights reserved.
 */
@Singleton
@Component(modules = arrayOf(PresenterModule::class, DataModule::class))
interface PresenterComponent {

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

}
