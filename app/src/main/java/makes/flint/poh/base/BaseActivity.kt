package makes.flint.poh.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import makes.flint.poh.Injection.Components.PresenterComponent
import org.jetbrains.anko.AnkoLogger

/**
 * BaseActivity
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
open class BaseActivity : AppCompatActivity(), AnkoLogger {

    internal lateinit var presenter: BaseContractPresenter<*>

    protected fun getBaseApplication(): BaseApplication = application as BaseApplication

    protected fun getPresenterComponent() : PresenterComponent = getBaseApplication().getPresenterComponent()

    protected fun attachPresenter(presenter:BaseContractPresenter<*>) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    fun showToast(stringId : Int, toastLength: Int) {
        Toast.makeText(this,getString(stringId), toastLength).show()
    }

    fun showLoading() {

    }

    fun hideLoading() {

    }

    fun showError(stringId : Int) {
        showToast(stringId, Toast.LENGTH_SHORT)
    }

}