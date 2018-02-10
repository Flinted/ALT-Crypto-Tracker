package makes.flint.poh.injection.components

import dagger.Component
import makes.flint.poh.injection.modules.DataModule
import makes.flint.poh.injection.modules.PresenterModule
import makes.flint.poh.ui.main.MainPresenter
import makes.flint.poh.ui.market.MarketPresenter
import makes.flint.poh.ui.market.coinDetail.CoinDetailPresenter
import makes.flint.poh.ui.market.coinlist.CoinListAdapterPresenter
import makes.flint.poh.ui.splash.SplashPresenter
import makes.flint.poh.ui.tracker.TrackerPresenter
import makes.flint.poh.ui.tracker.addCoinDialog.AddCoinDialogPresenter
import makes.flint.poh.ui.tracker.summary.summaryFragments.SummaryPresenter
import makes.flint.poh.ui.tracker.trackerEntryDialog.TrackerEntryDialogPresenter
import makes.flint.poh.ui.tracker.trackerEntryDialog.transactionsList.TransactionsAdapterPresenter
import makes.flint.poh.ui.tracker.trackerList.TrackerAdapterPresenter
import javax.inject.Singleton

/**
 * PresenterComponent
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
@Singleton
@Component(modules = arrayOf(PresenterModule::class, DataModule::class))
interface PresenterComponent {
    fun provideMainPresenter(): MainPresenter

    fun provideCoinListAdapterPresenter(): CoinListAdapterPresenter

    fun provideMarketPresenter(): MarketPresenter

    fun provideCoinDetailPresenter(): CoinDetailPresenter

    fun provideTrackerPresenter(): TrackerPresenter

    fun provideTrackerAdapterPresenter(): TrackerAdapterPresenter

    fun provideAddCoinDialogPresenter(): AddCoinDialogPresenter

    fun provideTrackerEntryDialogPresenter(): TrackerEntryDialogPresenter

    fun provideTransactionsAdapterPresenter(): TransactionsAdapterPresenter

    fun provideSplashPresenter(): SplashPresenter

    fun provideSummaryPresenter(): SummaryPresenter
}
