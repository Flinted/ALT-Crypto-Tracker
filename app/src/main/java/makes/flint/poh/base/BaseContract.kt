package makes.flint.poh.base

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
    fun attachView(view: V)

    fun detachView()

    fun initialise()
}