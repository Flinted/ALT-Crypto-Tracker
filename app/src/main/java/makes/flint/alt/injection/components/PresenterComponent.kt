package makes.flint.alt.injection.components

import dagger.Component
import makes.flint.alt.injection.modules.DataModule
import makes.flint.alt.injection.modules.PresenterModule
import makes.flint.alt.ui.main.MainPresenter
import makes.flint.alt.ui.market.MarketPresenter
import makes.flint.alt.ui.market.coinDetail.CoinDetailPresenter
import makes.flint.alt.ui.market.coinlist.CoinListAdapterPresenter
import makes.flint.alt.ui.settings.SettingsPresenter
import makes.flint.alt.ui.snapshot.SnapShotPresenter
import makes.flint.alt.ui.splash.SplashPresenter
import makes.flint.alt.ui.tracker.TrackerPresenter
import makes.flint.alt.ui.tracker.addCoinDialog.AddCoinDialogPresenter
import makes.flint.alt.ui.tracker.summary.summaryFragments.SummaryPresenter
import makes.flint.alt.ui.tracker.trackerEntryDialog.TrackerEntryDialogPresenter
import makes.flint.alt.ui.tracker.trackerEntryDialog.transactionsList.TransactionsAdapterPresenter
import makes.flint.alt.ui.tracker.trackerList.TrackerAdapterPresenter
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

    fun provideSettingsPresenter(): SettingsPresenter

    fun provideSnapShotPresenter(): SnapShotPresenter
}
