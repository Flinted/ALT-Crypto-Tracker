package this.chrisdid.alt.injection.components

import dagger.Component
import this.chrisdid.alt.injection.modules.DataModule
import this.chrisdid.alt.injection.modules.PresenterModule
import this.chrisdid.alt.ui.constraintui.addCoin.AddCoinPresenter
import this.chrisdid.alt.ui.constraintui.coinDetail.coinDetailChart.CoinDetailChartPresenter
import this.chrisdid.alt.ui.constraintui.coinDetail.coinDetailSummary.CoinDetailPresenter
import this.chrisdid.alt.ui.constraintui.coinlist.CoinListPresenter
import this.chrisdid.alt.ui.constraintui.coinlist.coinListAdapter.CoinListAdapterPresenter
import this.chrisdid.alt.ui.constraintui.layoutCoordinator.LayoutPresenter
import this.chrisdid.alt.ui.constraintui.trackerBarChart.TrackerBarChartPresenter
import this.chrisdid.alt.ui.constraintui.trackerEntryDetail.TrackerDetailDialogPresenter
import this.chrisdid.alt.ui.constraintui.trackerList.TrackerListPresenter
import this.chrisdid.alt.ui.constraintui.trackerList.trackerListAdapter.TrackerAdapterPresenter
import this.chrisdid.alt.ui.constraintui.trackerSummary.PortfolioSummaryPresenter
import this.chrisdid.alt.ui.settings.SettingsPresenter
import this.chrisdid.alt.ui.tracker.trackerEntryDialog.transactionsList.TransactionsAdapterPresenter
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
