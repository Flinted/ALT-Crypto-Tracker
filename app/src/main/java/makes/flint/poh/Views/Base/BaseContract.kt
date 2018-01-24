package makes.flint.poh.Views.Base

/**
 * BaseContract
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */

interface BaseContractView {
    fun showLoading()

    fun hideLoading()

    fun showError(stringId: Int)
}


interface BaseContractPresenter<in V:BaseContractView> {
    fun attachView(bridge: V)

    fun detachView()

    fun initialise()
}