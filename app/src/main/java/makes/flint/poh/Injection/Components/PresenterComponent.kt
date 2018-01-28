package makes.flint.poh.injection.components

import dagger.Component
import makes.flint.poh.injection.modules.DataModule
import makes.flint.poh.injection.modules.PresenterModule
import makes.flint.poh.ui.coinlist.CoinListAdapterPresenter
import makes.flint.poh.ui.main.MainPresenter
import makes.flint.poh.ui.market.MarketPresenter
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
}